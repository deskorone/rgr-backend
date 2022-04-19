package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaginationProductResponse {

    private List<ProductLiteResponse> products;

    private int totalCount;

    public static PaginationProductResponse build(Page<Product> products){
        return new PaginationProductResponse(
                products.stream().map(ProductLiteResponse::build).collect(Collectors.toList()),
                products.getTotalPages()
        );
    }



    public static PaginationProductResponse buildForUser(Page<Product> products, User user){
        return new PaginationProductResponse(
                products.stream().map(e-> ProductLiteResponse.buildForUser(e, user)).collect(Collectors.toList()),
                products.getTotalPages()
        );
    }


}
