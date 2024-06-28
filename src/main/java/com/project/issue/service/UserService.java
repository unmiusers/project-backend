package com.project.issue.service;

import com.project.issue.model.User;
import com.project.issue.model.LoginHistory;
import com.project.issue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setName(user.getName());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                }).orElse(false);
    }

    public List<Map<String, Object>> getUserLoginHistory(Long userId) {
        List<LoginHistory> loginHistories = userRepository.findLoginHistoryByUserId(userId);
        return loginHistories.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", history.getDate().toString());
            map.put("ip", history.getIp());
            return map;
        }).collect(Collectors.toList());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
