package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductLiteResponse {

    private  static String url = "http://localhost:8080/api/products/get/photo/%s";
    private Long id;
    private String photo;
    private String name;
    private Integer price;
    private Integer available;
    private boolean isFavorite;
    private Double rating;


    public static ProductLiteResponse build(Product product, Double rating){
        ProductInfo productInfo = product.getProductInfo();

        return new ProductLiteResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                productInfo.getName(),
                productInfo.getPrice(),
                productInfo.getNumber(),
                false,
                rating != null ? rating : 0
        );

    }

    public static ProductLiteResponse buildForUser(Product product, User user, Double rating){
        boolean isFav = user.getUserProfile().getFavorites()
                .getProducts()
                .stream()
                .anyMatch(e-> e.getId().equals(product.getId()));

        return new ProductLiteResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getProductInfo().getNumber(),
                isFav,
                rating != null ? rating : 0
        );


    }
}
