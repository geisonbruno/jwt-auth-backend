package br.com.geisonbrunodev.jwtauthappbackend.entity;

import br.com.geisonbrunodev.jwtauthappbackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @NotBlank(message = "O campo [username] é obrigatório")
    @Pattern(regexp = "\\S+", message = "O campo [username] não pode conter espaços em branco")
    @Column(unique = true)
    private String username;

    @Email(message = "O campo [email] deve ser um email válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @JsonIgnore
    @Length(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled;
}
