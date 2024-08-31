package com.fernandocanabarro.desafio_bossabox_tools.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fernandocanabarro.desafio_bossabox_tools.config.SecretUtils;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.User;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.RoleRepository;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.UserRepository;
import com.fernandocanabarro.desafio_bossabox_tools.services.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private String secret = SecretUtils.secret;

    @Transactional
    public UserDTO register(RegistrationRequestDTO dto){
        User user = new User();
        toEntity(user,dto);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    private void toEntity(User user, RegistrationRequestDTO dto) {
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.addRole(roleRepository.findByAuthority("ROLE_USER"));
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto){
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) {
            throw new UnauthorizedException("Credenciais Inválidas");
        }
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(), dto.getPassword());
        Authentication response = authenticationManager.authenticate(authentication);
        String jwt = "";
        if (response.isAuthenticated()) {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                jwt = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(response.getName())
                    .withClaim("username", response.getName())
                    .withClaim("authorities", response.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(86400L))
                    .sign(algorithm);
        }
        return new LoginResponseDTO(jwt, 86400L);
    }
}
