package com.example.demo.dto;

import com.example.demo.model.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
public class UserDTO {
    private String token;
    private String username;
    private String password;
    private String id;

    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .username(this.username)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}
