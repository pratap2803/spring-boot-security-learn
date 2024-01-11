package com.learn.controllers;

import com.learn.models.User;
import com.learn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // all users
    @GetMapping("/")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}")
    public User getUserbyUsername(@PathVariable String username){
        return this.userService.getUser(username);
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user){
        return this.userService.addUser(user);
    }
}
