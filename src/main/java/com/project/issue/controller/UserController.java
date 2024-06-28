package com.project.issue.controller;

import com.project.issue.model.User;
import com.project.issue.service.UserService;
import com.project.issue.util.JwtUtil;
import com.project.issue.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedEndpoint() {
        return ResponseEntity.ok("This is an unprotected endpoint");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/login-history")
    public ResponseEntity<List<Map<String, Object>>> getUserLoginHistory(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserLoginHistory(id));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return responseUtil.generateResponse("Username is already taken", HttpStatus.BAD_REQUEST, null);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.saveUser(user);
        return responseUtil.generateResponse("User registered successfully", HttpStatus.OK, savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        Optional<User> optionalUser = userService.findByUsername(user.getUsername());
        if (optionalUser.isPresent() && passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return responseUtil.generateResponse("Login successful", HttpStatus.OK, token);
        }
        return responseUtil.generateResponse("Invalid username or password", HttpStatus.UNAUTHORIZED, null);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            return responseUtil.generateResponse("User details fetched successfully", HttpStatus.OK, optionalUser.get());
        }
        return responseUtil.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
    }
}
