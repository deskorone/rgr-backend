package com.rgr.storeApp.controllers.admin;



import com.rgr.storeApp.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get/store")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStore(@RequestParam String email){
        return ResponseEntity.ok(adminService.getStore(email));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ban")
    public ResponseEntity<?> addBan(@RequestParam Long id) {
        adminService.addBan(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ban")
    public ResponseEntity<?> removeBan(@RequestParam Long id) {
        adminService.removeBan(id);
        return ResponseEntity.ok().build();
    }


}
