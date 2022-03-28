package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@AllArgsConstructor
public class ProductLiteResponse {

    private  static String url = "localhost:8080/api/products/get/photo/%s";
    private Long id;
    private String photo;
    private String name;
    private Integer price;
    private Integer available;
    private boolean isFavorite;
    private Double rating;


    public static ProductLiteResponse build(Product product, Double rating){
        return new ProductLiteResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getProductInfo().getNumber(),
                false,
                rating
        );

    }

    public static ProductLiteResponse buildForUser(Product product, User user, Double rating){
        boolean isFav = user.getUserProfile().getFavorites()
                .getProducts()
                .stream()
                .anyMatch(e-> e.getId() == product.getId());
        return new ProductLiteResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getProductInfo().getNumber(),
                isFav,
                rating
        );


    }
}
