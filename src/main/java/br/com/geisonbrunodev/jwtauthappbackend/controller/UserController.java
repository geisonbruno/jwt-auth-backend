package br.com.geisonbrunodev.jwtauthappbackend.controller;

import br.com.geisonbrunodev.jwtauthappbackend.dto.LoginDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.TokenDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserRegisterDTO;
import br.com.geisonbrunodev.jwtauthappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
