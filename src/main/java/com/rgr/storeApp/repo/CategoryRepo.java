package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);


    @Query(nativeQuery = true, value = "select * from categories c order by (select count(*) from categories cat inner join product_categories as pcat on pcat.category_id = c.id) desc;")
    Optional<List<Category>> getAllCat();





}
