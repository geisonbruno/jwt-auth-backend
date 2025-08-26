package br.com.geisonbrunodev.jwtauthappbackend.service;

import br.com.geisonbrunodev.jwtauthappbackend.dto.LoginDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.TokenDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserDTO;
import br.com.geisonbrunodev.jwtauthappbackend.dto.UserRegisterDTO;
import br.com.geisonbrunodev.jwtauthappbackend.entity.User;
import br.com.geisonbrunodev.jwtauthappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public TokenDTO register(UserRegisterDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateToken(savedUser.getUsername(), savedUser.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getUsername(), savedUser.getRole().name());

        return new TokenDTO(accessToken, refreshToken);
    }

    public TokenDTO authenticate(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getRole().name());

        return new TokenDTO(accessToken, refreshToken);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole()))
                .toList();
    }
}
