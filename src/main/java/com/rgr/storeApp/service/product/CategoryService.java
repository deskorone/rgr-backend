package com.rgr.storeApp.service.product;


import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> convertCategory(List<String> list){
        List<Category> categories = list.stream()
                .map((c)->{
                    Optional<Category> category = categoryRepo.findByName(c);
                    if(!category.isPresent()){
                        return categoryRepo.save(new Category(c));
                    }else {
                        return category.get();
                    }
                }).collect(Collectors.toList());
        return categories;
    }

}
