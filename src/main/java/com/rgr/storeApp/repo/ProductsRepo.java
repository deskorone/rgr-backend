package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {


    //select * from product p inner join product_info as pc on p.product_info_id = pc.id inner join product_categories as pcat on pcat.product_id = p.id inner join categories cat on cat.id = pcat.category_id where pc.name like '%test%' or cat.name like '%2%';
    @Query(value = "SELECT *  FROM product p inner join product_info as pi on pi.id = p.product_info_id where p.store_id = :#{#store.id}", nativeQuery = true)
    List<Product> findAllByStore(@Param("store") Store store);

    //@Query(value = "SELECT * FROM product p where p.id = :id", nativeQuery = true)
    Optional<Product> findById(@Param("id") Long id);

    Page<Product> findDistinctByCategories_NameLikeOrProductInfo_NameLike(String categoryName, String productName, Pageable pageable); //WORK

    @Query(value = "select * from product p " +
            "inner join product_info as pc on p.product_info_id = pc.id " +
            "where pc.name like %:product% ", nativeQuery = true)
    Page<Product> findWhereName(@Param("product") String product, Pageable pageable);

}

