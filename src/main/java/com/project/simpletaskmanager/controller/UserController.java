package com.project.simpletaskmanager.controller;

import com.project.simpletaskmanager.entity.User;
import com.project.simpletaskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Register a new user", description = "Create a new user account with a unique username and email")
    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        User newUser =  userService.registerUser(user);
        return newUser;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        System.out.println(user.toString());

        return userService.verify(user,authenticationManager);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getByEmail(@RequestParam String email){
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/hello")
    public String greet(HttpServletRequest request){
        return "Hello "+ request.getSession().getId();
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
