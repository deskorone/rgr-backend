package com.rgr.storeApp.controllers.profile;


import com.rgr.storeApp.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class TokenController {


    private  final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public TokenController(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @RequestMapping(value = "/api/accept/{token}", method = RequestMethod.GET)
    public String accept(@PathVariable("token") String token, ModelMap model){
        System.out.println(token);
        model.addAttribute("conf", confirmationTokenService.acceptToken(token));
        return "emailAccept";
    }


}
