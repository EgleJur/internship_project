package com.internship.userservice.controller;

import com.internship.userservice.model.Device;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;
import com.internship.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserCreationDTO userDetails) {
        return userService.createUser(userDetails);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserCreationDTO userDetails) {

        try {
            UserDTO userDTO = userService.updateUser(userId, userDetails);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {

        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    @GetMapping("/{userId}/devices")
    public ResponseEntity<List<Device>> getUserDevices(@PathVariable Long userId) {
        List<Device> devices = userService.getUserDevices(userId);
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }
}
