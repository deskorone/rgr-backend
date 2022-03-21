package com.rgr.storeApp.service.product;

import com.rgr.storeApp.dto.product.BuyResponse;
import com.rgr.storeApp.dto.ProductRequest;
import com.rgr.storeApp.dto.ProductResponse;
import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.*;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import com.rgr.storeApp.service.favorites.profile.buy.BuyService;
import com.rgr.storeApp.service.find.FindService;
import com.rgr.storeApp.service.store.StoreService;
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
    private final CategoryService categoryService;
    private final ProductPhotoRepo productPhotoRepo;
    private final BuyService buyService;
    private final StoreService storeService;
    private final FindService findService;

    @Autowired
    public ProductService(ProductsRepo productsRepo,
                          UsersRepo usersRepo,
                          CategoryService categoryService,
                          ProductPhotoRepo productPhotoRepo,
                          BuyService buyService, StoreService storeService, FindService findService) {

        this.productsRepo = productsRepo;
        this.usersRepo = usersRepo;
        this.categoryService = categoryService;
        this.productPhotoRepo = productPhotoRepo;
        this.buyService = buyService;
        this.storeService = storeService;
        this.findService = findService;
    }




    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest,
                                      String email,
                                      MultipartFile multipartFile,
                                        MultipartFile [] multipartFiles){
        Store store = usersRepo.findByEmail(email).get().getStore();
        Product product = new Product();
        product.setStore(store);
        product.setId_code(UUID.randomUUID().toString());
        List<Category> categories = categoryService.convertCategory(productRequest.getCategories());
        product.setCategories(categories);
        ProductInfo productInfo = new ProductInfo();
        if(multipartFiles != null){
            productInfo.setProductPhotos(Arrays.stream(multipartFiles)
                    .map(e->savePhoto(e, product))
                    .collect(Collectors.toList()));
        }else {
            productInfo.setProductPhotos(null);
        }
        productInfo.setName(productRequest.getName());
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


    public List<ProductResponse> getAll(){
        User user = findService.getUser(findService.getEmailFromAuth());
        Store store = user.getStore();
        return productsRepo
                .findAllByStore(store)
                .stream()
                .map(ProductResponse::build)
                .collect(Collectors.toList());
    }


    public Product getProduct(Long id){
        return productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found :("));
    }

    @Transactional
    public BuyResponse buy(){
        User user = findService.getUser(findService.getEmailFromAuth());
        UserProfile userProfile = user.getUserProfile();
        return buyService.addBuy(userProfile);
    }



    public List<ProductLiteResponse> getProductsInfo(String email){
        Store store = usersRepo.findByEmail(email).orElseThrow(()->new NotFound("SalesMan not found")).getStore();
        List<ProductLiteResponse> productLiteResponses = store.getProducts()
                .stream()
                .map((e)->{
                    return ProductLiteResponse.build(e);
                })
                .collect(Collectors.toList());
        return productLiteResponses;
    }


    public void deleteProduct(String email, Long id){
        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("user not found"));
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product NOT FOUND :(")); // find by id and producer? // maybe delete photo?
        if (storeService.checkProduct(user, product)) {
            productsRepo.delete(product);
        }else {
            throw new NotPrivilege("No privilegies");
        }
    }




}
