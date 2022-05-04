package com.rgr.storeApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ConfigStrings {

    public static String ngrokUrl;

    @Autowired
    public ConfigStrings(@Value("${app.url}") String url) {
        ConfigStrings.ngrokUrl = url;
    }


}
