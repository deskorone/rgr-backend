package com.rgr.storeApp.service.find;

import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FindService {

    private final UsersRepo usersRepo;


    @Autowired
    public FindService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }


    public User getUser(String email){
        return usersRepo.findByEmail(email).orElseThrow(()->new NotFound("UserNOt found"));
    }

    public String getEmailFromAuth(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
