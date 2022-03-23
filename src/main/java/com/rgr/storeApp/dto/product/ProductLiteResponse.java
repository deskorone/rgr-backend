package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductLiteResponse {

    private  static String url = "localhost:8080/api/products/get/photo/%s";
    private Long id;
    private String photo;
    private String name;
    private Integer price;
    private Integer available;
    private Double rating;


    public static ProductLiteResponse build(Product product){
        Double rait;
        if(product.getReviews().size() != 0) {
            Double rating = product.getReviews()
                    .stream()
                    .mapToDouble(e -> {
                        Double raiting = Double.parseDouble(Integer.toString(e.getRating()));
                        return raiting;
                    }).sum();
            rait = rating/product.getReviews().size();
        }else {
            rait = 0D;
        }
        return new ProductLiteResponse(
                product.getId(),
                String.format(url, product.getProductInfo().getMainPhoto().getPath()),
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getProductInfo().getNumber(),
                rait
        );

    }
}
