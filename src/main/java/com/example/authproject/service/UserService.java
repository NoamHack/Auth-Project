package com.example.similarfingerprint.service;

import com.example.similarfingerprint.dto.UserDTO;
import com.example.similarfingerprint.entity.UserEntity;
import com.example.similarfingerprint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // DTO to Entity
    public UserEntity convertToEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setUserData(userDTO.getUserData());
        return user;
    }

    // Entity to DTO
    public UserDTO convertToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserData(userEntity.getUserData());
        return userDTO;
    }

    public UserDTO getUserById(Long id) {
        UserEntity user =  userRepository.findById(id).orElse(null);
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = convertToEntity(userDTO);
        userRepository.save(user);
        return userDTO;
    }
}
