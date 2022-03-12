package com.rgr.storeApp.dao;


import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private  static String url = "localhost:8080/api/auth/test/product/photo/%s";

    private Long id;

    private String maimImage;

    private List<String> images;

    private Integer price;

    private String description;

    private List<String> categoryes;

    private Integer available;

    private List<Review> reviews;

    private String materials;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", maimImage='" + maimImage + '\'' +
                ", images=" + images +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", categoryes=" + categoryes +
                ", available=" + available +
                ", reviews=" + reviews +
                ", materials='" + materials + '\'' +
                '}';
    }

    public static ProductResponse build(Product product){
        return new ProductResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                null,
                product.getProductInfo().getPrice(),
                product.getProductInfo().getDescription(),
                product.getCategories().stream().map(e->e.getName()).collect(Collectors.toList()),
                product.getProductInfo().getNumber(),
                product.getReviews(),
                product.getProductInfo().getMaterials()
        );
    }


}
