package com.hisa.backend.security.response;


import com.hisa.backend.bean.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;

}
