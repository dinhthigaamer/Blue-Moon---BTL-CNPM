package com.example.project.auth.mapper;

import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setRole(user.getRole().name());
        return dto;
    }

    public User toEntity(RegisterRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCccd(dto.getCccd());
        if (dto.getRole() != null) {
            user.setRole(User.Role.valueOf(dto.getRole()));
        } else {
            user.setRole(User.Role.ACCOUNTANT);
        }
        return user;
    }
}
