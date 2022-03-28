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
    private final ProductsRepo productsRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo, ProductsRepo productsRepo) {
        this.categoryRepo = categoryRepo;
        this.productsRepo = productsRepo;
    }

    public List<Category> convertCategory(List<String> list) {
        List<Category> categories = list.stream()
                .map((c) -> {
                    Optional<Category> category = categoryRepo.findByName(c);
                    if (!category.isPresent()) {
                        return categoryRepo.save(new Category(c));
                    } else {
                        return category.get();
                    }
                }).collect(Collectors.toList());
        return categories;
    }

}
