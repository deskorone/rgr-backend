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
    @Query(value = "SELECT *  FROM product p inner join product_info as pi on pi.id = p.product_info_id  inner join reviews as r on r.product_id = p.id where p.store_id = :#{#store.id}", nativeQuery = true)
    List<Product> findAllByStore(@Param("store") Store store);


    @Query(value = "SELECT * FROM product p " +
            "inner join  product_categories as pc on pc.product_id = p.id " +
            "inner join reviews as r on r.product_id = p.id  " +
            "inner join categories as c on c.id = pc.category_id where c.name=:category",nativeQuery = true)
    Page<Product> getByCategory(@Param("category") String category, Pageable pageable);




    Optional<Product> findById(@Param("id") Long id);

    @Query(value = "select * from product p " +
            "inner join product_info as pc on p.product_info_id = pc.id " +
            "where pc.name like %:product% ", nativeQuery = true)
    Page<Product> findWhereName(@Param("product") String product, Pageable pageable);


    @Query(value = "SELECT * FROM product p inner join product_info as pi on p.product_info_id = pi.id  inner join reviews as r on r.product_id = p.id where pi.description like %:desc%", nativeQuery = true)
    Page<Product> getByDescription(@Param("desc") String description, Pageable pageable);

    @Query(value = "select p.*, pi.* from product p inner join product_categories as pc on pc.product_id = p.id inner join product_info as pi on pi.id = p.product_info_id inner join reviews as r on r.product_id = p.id inner join categories as c on pc.category_id = c.id where c.name = 'cat45' order by p.id desc limit 10;", nativeQuery = true)
    List<Product> getProductsByCategory(@Param("name") String name);



}

