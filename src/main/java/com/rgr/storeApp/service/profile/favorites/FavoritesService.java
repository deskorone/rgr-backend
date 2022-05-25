package com.rgr.storeApp.service.profile.favorites;

import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.product.Favorites;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.repo.FavoritesRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UserProfileRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final FavoritesRepo favoritesRepo;
    private final FindService findService;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;

    @Autowired
    public FavoritesService(FavoritesRepo favoritesRepo,
                            FindService findService, UsersRepo usersRepo,
                            ProductsRepo productsRepo) {
        this.favoritesRepo = favoritesRepo;
        this.findService = findService;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }

    public List<ProductLiteResponse> addFavoriteProduct(Long id){
        Favorites favorites = findService.getUser(findService.getEmailFromAuth())
                .getUserProfile()
                .getFavorites();
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found"));
        favorites.getProducts().add(product);
        return favoritesRepo.save(favorites).getProducts().stream().map(ProductLiteResponse::build).collect(Collectors.toList());
    }

    public List<ProductLiteResponse> deleteProduct(Long id){
        Favorites favorites = findService.getUser(findService.getEmailFromAuth())
                .getUserProfile()
                .getFavorites();
        Product product = productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found"));
        favorites.remove(product);
        return favoritesRepo.save(favorites).getProducts().stream().map(ProductLiteResponse::build).collect(Collectors.toList());
    }






}
