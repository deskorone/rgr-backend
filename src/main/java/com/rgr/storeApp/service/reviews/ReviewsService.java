package com.rgr.storeApp.service.reviews;


import com.rgr.storeApp.dto.product.ProductResponse;
import com.rgr.storeApp.dto.ReviewRequest;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Review;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.ReviewRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {

    private final ProductsRepo productsRepo;
    private final FindService findService;

    @Autowired
    public ReviewsService(ProductsRepo productsRepo, ReviewRepo reviewRepo, UsersRepo usersRepo, FindService findService) {
        this.productsRepo = productsRepo;
        this.findService = findService;
    }


    public ProductResponse addReview( ReviewRequest reviewRequest, Long productId){
        if(reviewRequest.getRating() > 5 || reviewRequest.getRating() < 0){
            throw new RuntimeException("Error raiting value");
        }else {
            User user = findService.getUser(findService.getEmailFromAuth());
            Product product = productsRepo.findById(productId).orElseThrow(() -> new NotFound("Product NOT FOUND"));
            Review review = new Review(reviewRequest.getReviewText(), reviewRequest.getRating());
            review.setUser(user);
            review.setProduct(product);
            product.getReviews().add(review);
            productsRepo.save(product);
            return ProductResponse.build(product);
        }
    }







}
