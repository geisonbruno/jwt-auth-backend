package br.com.geisonbrunodev.jwtauthappbackend.controller;

import br.com.geisonbrunodev.jwtauthappbackend.dto.LoginDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.TokenDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserRegisterDTO;
import br.com.geisonbrunodev.jwtauthappbackend.entity.User;
import br.com.geisonbrunodev.jwtauthappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public TokenDTO register(@RequestBody UserRegisterDTO userDTO) {
        return userService.register(userDTO);
    }

    @GetMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        return userService.authenticate(loginDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(dto);
    }


}
