package br.com.geisonbrunodev.jwtauthappbackend.dto;

import br.com.geisonbrunodev.jwtauthappbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
}