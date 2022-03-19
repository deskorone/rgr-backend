package com.rgr.storeApp.service.reviews;


import com.rgr.storeApp.dto.ProductResponse;
import com.rgr.storeApp.dto.ReviewRequest;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Review;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.ReviewRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {

    private final ProductsRepo productsRepo;
    private final ReviewRepo reviewRepo;
    private final UsersRepo usersRepo;

    @Autowired
    public ReviewsService(ProductsRepo productsRepo, ReviewRepo reviewRepo, UsersRepo usersRepo) {
        this.productsRepo = productsRepo;
        this.reviewRepo = reviewRepo;
        this.usersRepo = usersRepo;
    }


    public ProductResponse addReview(String email, ReviewRequest reviewRequest, Long productId){
        if(reviewRequest.getRating() > 5 || reviewRequest.getRating() < 0){
            throw new RuntimeException("Error raiting value");
        }else {
            User user = usersRepo.findByEmail(email).orElseThrow(() -> new NotFound("User not found"));
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
