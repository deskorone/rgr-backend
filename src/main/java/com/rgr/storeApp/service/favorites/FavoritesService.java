package com.rgr.storeApp.service.favorites;

import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.product.Favorites;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.repo.FavoritesRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UserProfileRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService {

    private final FavoritesRepo favoritesRepo;
    private final UserProfileRepo userProfileRepo;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;

    @Autowired
    public FavoritesService(FavoritesRepo favoritesRepo,
                            UserProfileRepo userProfileRepo,
                            UsersRepo usersRepo,
                            ProductsRepo productsRepo) {
        this.favoritesRepo = favoritesRepo;
        this.userProfileRepo = userProfileRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }

    public Favorites addFavoriteProduct(String email, Long id){
        Favorites favorites = usersRepo.findByEmail(email)
                .orElseThrow(()-> new NotFound(("User not found")))
                .getUserProfile()
                .getFavorites();
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found"));
        favorites.getProducts().add(product);
        return favoritesRepo.save(favorites);
    }

    public Favorites deleteProduct(String email, Long id){
        Favorites favorites = usersRepo.findByEmail(email)
                .orElseThrow(()-> new NotFound(("User not found")))
                .getUserProfile()
                .getFavorites();
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found"));
        favorites.remove(product);
        return favoritesRepo.save(favorites);
    }






}
