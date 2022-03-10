package com.rgr.storeApp.service.product;

import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.dao.ProductResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.*;
import com.rgr.storeApp.repo.ProducerRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductsRepo productsRepo;
    private final UsersRepo usersRepo;
    private final ProducerRepo producerRepo;
    private final CategoryService categoryService;
    private final ProductInfoService productInfoService;

    @Autowired
    public ProductService(ProductsRepo productsRepo,
                          UsersRepo usersRepo,
                          ProducerRepo producerRepo,
                          CategoryService categoryService,
                          ProductInfoService productInfoService) {

        this.productsRepo = productsRepo;
        this.usersRepo = usersRepo;
        this.producerRepo = producerRepo;
        this.categoryService = categoryService;
        this.productInfoService = productInfoService;
    }

    public Product addProduct(ProductRequest productRequest, String email, MultipartFile file){

        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("User not found"));
        Producer producer = producerRepo.findByUser(user).orElseThrow(()-> new NotFound("NOT FOUND"));
        Product product = new Product();
        ProductInfo productInfo = new ProductInfo();
        product.setProducer(producer);
        product.setCategories(null);
        productInfo.setDescription(productRequest.getDescription());
        productInfo.setProductPhotos(null);
        productInfo.setMaterials("materials");
        try{
            ProductPhoto mainPhoto = new ProductPhoto(file.getBytes());
            productInfo.setMainPhoto(mainPhoto);
        }catch (IOException e){
            e.printStackTrace();
        }
        product.setProductInfo(productInfo);
        productsRepo.save(product);
        return product;
    }



}
