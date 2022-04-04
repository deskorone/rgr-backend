package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductInfoRepo extends JpaRepository<ProductInfo, Long> {


//    @Query(value = "select p from ProductInfo p join fetch Product pr where   = :id")
//    ProductInfo findByIdFetchProduct(@Param("id") Long id);

    @Query(value = "SELECT * FROM product_info p inner join photo as ph on ph.product_id = p.id where p.id = :#{#product.productInfo.id}", nativeQuery = true)
    ProductInfo getByProduct(@Param("product") Product product);


}
