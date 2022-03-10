package com.rgr.storeApp.service.product;


import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.models.product.ProductInfo;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoService {


    public ProductInfo buildFromRequest(ProductRequest request){

        return new ProductInfo(request.getDescription(), request.getInfo());
    }

}
