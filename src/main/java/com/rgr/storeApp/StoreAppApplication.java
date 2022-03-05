package com.rgr.storeApp;

import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.Role;
import com.rgr.storeApp.repo.RolesRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreAppApplication.class, args);
	}


}
