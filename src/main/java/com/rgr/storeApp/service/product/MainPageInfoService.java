package com.rgr.storeApp.service.product;


import com.rgr.storeApp.dto.product.CategoryMainDto;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.repo.CategoryRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainPageInfoService {

    private final CategoryRepo categoryRepo;
    private final ProductsRepo productsRepo;

    @Autowired
    public MainPageInfoService(CategoryRepo categoryRepo, ProductsRepo productsRepo) {
        this.categoryRepo = categoryRepo;
        this.productsRepo = productsRepo;
    }


    public List<CategoryMainDto> getActualProducts(){
        List<CategoryMainDto> categoryDtos = new ArrayList<>();
        List<Category> categories = categoryRepo.getAllCat().orElseThrow(()->new NotFound("Page not found"));
        categories.forEach(e->{
            categoryDtos.add(CategoryMainDto.build(e, productsRepo.getProductsByCategory(e.getName())));
        });
        return categoryDtos;
    }

}



