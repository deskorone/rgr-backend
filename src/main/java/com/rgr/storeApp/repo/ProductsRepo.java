package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {


    //select * from product p inner join product_info as pc on p.product_info_id = pc.id inner join product_categories as pcat on pcat.product_id = p.id inner join categories cat on cat.id = pcat.category_id where pc.name like '%test%' or cat.name like '%2%';
    List<Product> findAllByStore(Store store);

    Optional<Product> findById(Long id);

    //List<Product> findByCategories_NameLikeOrProductInfo_NameLike(String categoryName, String productName); //WORK

    @Query(value = "select p.* from product p " +
            "inner join product_info as pc on p.product_info_id = pc.id " +
            "inner join product_categories as pcat on pcat.product_id = p.id " +
            "inner join categories as cat on cat.id = pcat.category_id where pc.name like %:product% or cat.name like %:categoryParam% order by pc.price DESC ", nativeQuery = true)
    Page<Product> finWhereName(@Param("product") String product, @Param("categoryParam") String categoryParam, Pageable pageable);
}
