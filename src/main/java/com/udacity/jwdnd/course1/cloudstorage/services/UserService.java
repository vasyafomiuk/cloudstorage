package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HashService hashService;

    public UserService(UserRepository userRepository, HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findUserByUsername(username) == null;
    }

    public User createUser(User user) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setSalt(encodedSalt);
        u.setPassword(hashedPassword);
        return userRepository.save(u);
    }

    public boolean authenticate(String username, String password) {
        User u = getUser(username);
        if (u == null) {
            return false;
        }
        String hashedPassword = hashService.getHashedValue(password, u.getSalt());
        return hashedPassword.equals(u.getPassword());
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void delete(User user){
        userRepository.delete(user);
    }
}

