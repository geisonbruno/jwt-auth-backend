package br.com.geisonbrunodev.jwtauthappbackend.controller;

import br.com.geisonbrunodev.jwtauthappbackend.dto.LoginDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.TokenDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserRegisterDTO;
import br.com.geisonbrunodev.jwtauthappbackend.entity.User;
import br.com.geisonbrunodev.jwtauthappbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Valid
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody UserRegisterDTO userDTO) {
        TokenDTO token = userService.register(userDTO);
        return ResponseEntity
                .status(201)
                .body(token);
    }

    @Valid
    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        return userService.authenticate(loginDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> findAll() {
        return userService.findAllUsers();
    }
}
