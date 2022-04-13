package com.rgr.storeApp.service.product;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.repo.CategoryRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
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

    public List<Category> convertCategory(List<String> list) {
        return list.stream()
                .map((c) -> {
                    Optional<Category> category = categoryRepo.findByName(c);
                    return category.orElseGet(() -> categoryRepo.save(new Category(c)));
                }).collect(Collectors.toList());
    }

    public List<Category> getAllCategoriesSorted(){
        return categoryRepo.getAllCat().orElseThrow(()-> new NotFound("Categories not found"));
    }

}
