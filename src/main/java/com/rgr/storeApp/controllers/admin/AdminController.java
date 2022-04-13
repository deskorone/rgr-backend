package com.rgr.storeApp.controllers.admin;


import com.rgr.storeApp.dto.admin.StoreReq;
import com.rgr.storeApp.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get/store")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStore(@RequestBody StoreReq storeReq){
        System.out.println(storeReq);
        return ResponseEntity.ok(adminService.getStore(storeReq.getEmail()));
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> get(){
        adminService.getStore("e1");
        return ResponseEntity.ok("Heloooo");
    }

}
