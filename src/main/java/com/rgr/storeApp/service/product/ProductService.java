package com.rgr.storeApp.service.product;

import com.rgr.storeApp.dao.BuyResponse;
import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.dao.ProductResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.*;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import com.rgr.storeApp.service.profile.buy.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductService {



    private final ProductsRepo productsRepo;
    private final UsersRepo usersRepo;
    private final ProducerRepo producerRepo;
    private final CategoryService categoryService;
    private final ProductInfoService productInfoService;
    private final ProductPhotoRepo productPhotoRepo;
    private final BuyService buyService;
    private final UserProfileRepo userProfileRepo;

    @Autowired
    public ProductService(ProductsRepo productsRepo,
                          UsersRepo usersRepo,
                          ProducerRepo producerRepo,
                          CategoryService categoryService,
                          ProductInfoService productInfoService, ProductPhotoRepo productPhotoRepo, BuyService buyService, UserProfileRepo userProfileRepo) {

        this.productsRepo = productsRepo;
        this.usersRepo = usersRepo;
        this.producerRepo = producerRepo;
        this.categoryService = categoryService;
        this.productInfoService = productInfoService;
        this.productPhotoRepo = productPhotoRepo;
        this.buyService = buyService;
        this.userProfileRepo = userProfileRepo;
    }







    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest,
                                      String email,
                                      MultipartFile multipartFile,
                                        MultipartFile [] multipartFiles){
        Producer producer = usersRepo.findByEmail(email).get().getProducer();
        Product product = new Product();
        product.setProducer(producer);
        product.setId_code(UUID.randomUUID().toString());
        List<Category> categories = categoryService.convertCategory(productRequest.getCategories());
        product.setCategories(categories);
        ProductInfo productInfo = new ProductInfo();
        if(multipartFiles!= null){
            productInfo.setProductPhotos(Arrays.stream(multipartFiles)
                    .map(e->savePhoto(e, product))
                    .collect(Collectors.toList()));
        }
        productInfo.setMainPhoto(savePhoto(multipartFile, product));
        productInfo.setPrice(productRequest.getPrice());
        productInfo.setNumber(productRequest.getNumber());
        productInfo.setDescription(productRequest.getDescription());
        productInfo.setMaterials(productRequest.getMaterials());
        product.setProductInfo(productInfo);
        productsRepo.save(product);
        return ProductResponse.build(product);

    }


    public ProductPhoto savePhoto(MultipartFile multipartFile, Product product){
        ProductPhoto productPhoto = new ProductPhoto();
        try{
            String name = Base64.getEncoder().encodeToString(multipartFile.getBytes()).substring(40, 80) + UUID.randomUUID().toString().substring(3,6) + ".png";
            File photo = new File(String.format("src/main/resources/static/images/%s", name));
            photo.createNewFile();
            try(OutputStream outputStream = new FileOutputStream(photo)) {
                outputStream.write(multipartFile.getBytes());
            }
            productPhoto.setPath(photo.getName()); //
        }catch (IOException e){
            e.printStackTrace();
        }
        productPhoto.setProduct(product);
        return productPhoto;
    }




    public byte [] getPhoto(String path){

        Optional<ProductPhoto> productPhoto = productPhotoRepo.findByPath(path);
        if(productPhoto.isPresent()){
            try{
                File file = new File((String.format("src/main/resources/static/images/%s", path)));
                InputStream stream = new FileInputStream(file);
                System.out.println("WORK");
                return stream.readAllBytes();
            }catch (IOException e){
                e.printStackTrace();
                throw new NotFound("Photo not found");
            }

        }else {
            throw new NotFound("Photo not found");
        }
    }


    public List<ProductResponse> getAll(String email){

        User user = usersRepo.findByEmail(email).orElseThrow(()->new NotFound("User not found"));
        Producer producer = user.getProducer();
        return productsRepo
                .findAllByProducer(producer)
                .stream()
                .map(ProductResponse::build)
                .collect(Collectors.toList());
    }


    public Product getProduct(Long id){
        return productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found :("));
    }

    @Transactional
    public BuyResponse buy(String email){
        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("Not found"));
        UserProfile userProfile = user.getUserProfile();
        buyService.addBuy(userProfile);
        return new BuyResponse();
    }


    public List<ProductResponse> getProductsInfo(String email){
        Producer producer = usersRepo.findByEmail(email).orElseThrow(()->new NotFound("SalesMan not found")).getProducer();
        List<Product> products = producer.getProducts();

        return  products.stream()
                .map(e->ProductResponse.build(e)).collect(Collectors.toList());
    }


    public void deleteProduct(String email, Long id){
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product NOT FOUND :(")); // find by id and producer? // maybe delete photo?
        productsRepo.delete(product);
    }





}
