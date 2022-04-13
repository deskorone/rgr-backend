package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.product.Category;
import com.rgr.storeApp.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMainDto {

    private String name;
    private List<ProductLiteResponse> products;


    public static CategoryMainDto build(Category category, List<Product> products){
        return new CategoryMainDto(category.getName(),
                products.stream().map((e)->
                        ProductLiteResponse.build(e, null))
                        .collect(Collectors.toList()));
    }



}
