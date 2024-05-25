package com.CollabTask.Controllers;

import com.CollabTask.models.AppUser;
import com.CollabTask.Services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            AppUser user = appUserService.getUser(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
        }
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        boolean isValidEmailAndPassword = appUserService.validateEmailAndPassword(user.getEmail(), user.getPassword());
        if (!isValidEmailAndPassword) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } else {
            Long userId = appUserService.getUserIdByEmail(user.getEmail());
            if (userId == null) {
                // Handle scenario when user ID is not found
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User ID not found");
            }
            return ResponseEntity.ok(userId);
        }
    }

}
