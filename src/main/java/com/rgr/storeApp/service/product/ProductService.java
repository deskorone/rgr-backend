package com.rgr.storeApp.service.product;

import com.rgr.storeApp.dto.product.ProductRequest;
import com.rgr.storeApp.dto.product.ProductResponse;
import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.dto.userProfile.DeliveryDto;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.*;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import com.rgr.storeApp.service.profile.buy.BuyService;
import com.rgr.storeApp.service.find.FindService;
import com.rgr.storeApp.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductsRepo productsRepo;
    private final CategoryService categoryService;
    private final ProductPhotoRepo productPhotoRepo;
    private final BuyService buyService;
    private final StoreService storeService;
    private final FindService findService;
    private final ReviewRepo reviewRepo;

    @Autowired
    public ProductService(ProductsRepo productsRepo,
                          CategoryService categoryService,
                          ProductPhotoRepo productPhotoRepo,
                          BuyService buyService, StoreService storeService,
                          FindService findService,
                          ReviewRepo reviewRepo) {
        this.productsRepo = productsRepo;
        this.categoryService = categoryService;
        this.productPhotoRepo = productPhotoRepo;
        this.buyService = buyService;
        this.storeService = storeService;
        this.findService = findService;
        this.reviewRepo = reviewRepo;
    }




    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest,
                                      MultipartFile multipartFile,
                                        MultipartFile [] multipartFiles){
        Store store = findService.getUser(findService.getEmailFromAuth()).getStore();
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
                return stream.readAllBytes();
            }catch (IOException e){
                throw new NotFound("Photo not found or path not found");
            }
        }else {
            throw new NotFound("Photo not found");
        }
    }


    public List<ProductResponse> getAllByStore(){
        Store store = findService.getUser(findService.getEmailFromAuth()).getStore();
        return productsRepo
                .findAllByStore(store)
                .stream()
                .map(ProductResponse::build)
                .collect(Collectors.toList());
    }


    public ProductResponse getProduct(Long id){
        return ProductResponse.build(findService.findProduct(id));
    }

    @Transactional
    public DeliveryDto buy(){
        User user = findService.getUser(findService.getEmailFromAuth());
        UserProfile userProfile = user.getUserProfile();
        return buyService.addBuy(userProfile);
    }



    public void deleteProduct(Long id){
        User user = findService.getUser(findService.getEmailFromAuth());
        Product product = findService.findProduct(id);
        if (storeService.checkProduct(user, product) || user.getRoles().stream().anyMatch(e-> e.getRole().equals(ERole.ROLE_ADMIN))) {
            productsRepo.delete(product);
        }else {
            throw new NotPrivilege("No privilegies");
        }
    }

    public List<ProductLiteResponse> findByName(String name, int count, int size) {
        try {
            Pageable pageable = PageRequest.of(count - 1,size);
            Page<Product> products = productsRepo.findWhereName(name, pageable);
            if(products.getSize() == 0){throw new NotFound(String.format("Product %s not found", name));}
            if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")){
                User user = findService.getUser(findService.getEmailFromAuth());
                return products.stream()
                        .map(e -> ProductLiteResponse.buildForUser(e, user, reviewRepo.getRating(e))).collect(Collectors.toList());
            }
            return products.stream().map(e->ProductLiteResponse.build(e, reviewRepo.getRating(e))).collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFound("Pageable error");
        }
    }






}
