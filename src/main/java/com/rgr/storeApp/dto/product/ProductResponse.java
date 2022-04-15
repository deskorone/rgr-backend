package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.dto.userProfile.UserProfileInfo;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Review;
import com.rgr.storeApp.models.product.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private  static String url = "http://localhost:8080/api/products/get/photo/%s";

    private Long id;

    private String maimImage;

    private List<String> images;

    private String name;

    private Integer price;

    private String description;

    private List<String> categoryes;

    private Integer available;

    private List<Review> reviews;

    private Double rating;

    private String materials;

    private Store store;

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
        List<String> images;
        if(product.getProductInfo().getProductPhotos() != null) {
            images =  product.getProductInfo().getProductPhotos()
                    .stream()
                    .map(e -> String.format(url, e.getPath()))
                    .collect(Collectors.toList());
        }else {
            images = null;
        }
        Double r;
        if(product.getReviews() != null) {
            Double rating = product.getReviews()
                    .stream()
                    .mapToDouble(e -> {
                        Double raiting = Double.parseDouble(Integer.toString(e.getRating()));
                        return raiting;
                    }).sum();
            r = rating/product.getReviews().size();
        }else {
            r = 0D;
        }

        return new ProductResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                images,
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getProductInfo().getDescription(),
                product.getCategories().stream().map(e->e.getName()).collect(Collectors.toList()),
                product.getProductInfo().getNumber(),
                product.getReviews(),
                r,
                product.getProductInfo().getMaterials(),
                product.getStore()
        );
    }


}
