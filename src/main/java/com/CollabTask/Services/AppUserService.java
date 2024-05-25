package com.CollabTask.Services;

import com.CollabTask.models.AppUser;
import com.CollabTask.Repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    private Optional<AppUser> logInUser;

    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public Optional<AppUser> findUserByEmail(String email) {
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("User information fetched successfully");
        }
        return user;
    }

    public boolean validateEmailAndPassword(String email, String password) {
        Optional<AppUser> userEntity = appUserRepository.findByEmail(email);
        if (userEntity.isPresent() && password.equals(userEntity.get().getPassword())) {
            logInUser = userEntity;
            return true;
        }
        return false;
    }

    public Long getUserIdByEmail(String email) {
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        return user.map(AppUser::getId).orElse(null);
    }
}
