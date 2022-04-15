package com.rgr.storeApp.controllers.profile;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ChecktestController {

    private final ProductsRepo productsRepo;
    private final EmailService emailService;

    @Autowired
    public ChecktestController(ProductsRepo productsRepo, EmailService emailService) {
        this.productsRepo = productsRepo;
        this.emailService = emailService;
    }

    @GetMapping("/check")
    public String get(Model model){

        List<Product> products = productsRepo.getProductsByCategory("cat");
        List<ProductLiteResponse> n = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            n.add(ProductLiteResponse.build(products.get(i), null));
        }
        model.addAttribute("sum", 1000);
        model.addAttribute("products", n);

        emailService.sendCheck("hello", "hi", "f", n);
        return "check";
    }


}
