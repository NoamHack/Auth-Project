package com.example.authproject.controller;
import com.example.authproject.service.DatabaseBackupService;
import com.example.authproject.auth.DatabaseSecurityManager;
import com.example.authproject.dto.UserDTO;
import com.example.authproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DatabaseSecurityManager securityManager;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        userDTO.setUserData(securityManager.decrypt(userDTO.getUserData()));
        return userDTO;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        userDTO.setUserData(securityManager.encrypt(userDTO.getUserData()));
        return userService.createUser(userDTO);
    }

    @PostMapping("/security/backup")
    public void createDatabaseBackup() throws IOException, InterruptedException {
        DatabaseBackupService.createBackup();
    }
}
