package com.fernandocanabarro.desafio_bossabox_tools.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.Role;
import com.fernandocanabarro.desafio_bossabox_tools.entities.User;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.RoleRepository;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.UserRepository;
import com.fernandocanabarro.desafio_bossabox_tools.services.exceptions.UnauthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    private RegistrationRequestDTO registrationRequestDTO;
    private User user;
    private LoginRequestDTO loginRequestDTO;
    private Authentication authentication;

    @BeforeEach
    public void setup() throws Exception{
        registrationRequestDTO = new RegistrationRequestDTO("ana","ana@gmail.com", "12345Az@");
        Role role = new Role("1", "ROLE_USER");
        user = new User("1","ana", "ana@gmail.com","12345Az@");
        user.addRole(role);
        loginRequestDTO = new LoginRequestDTO("ana@gmail.com", "12345Az@");
        authentication = new UsernamePasswordAuthenticationToken("ana@gmail.com", "122345Az@");
    }

    @Test
    public void registerShouldReturnUserDTOWhenDataIsValid(){
        when(passwordEncoder.encode("12345Az@")).thenReturn("jashdgiushaghaiuhgwgwuihg");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO response = userService.register(registrationRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getFullName()).isEqualTo("ana");
        assertThat(response.getEmail()).isEqualTo("ana@gmail.com");
        assertThat(response.getRoles().get(0).getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    public void loginShouldReturnLoginResponseDTOWhenDataIsValid(){
        when(userRepository.findByEmail("ana@gmail.com")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        LoginResponseDTO response = userService.login(loginRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isNotNull();
        assertThat(response.expiresIn()).isEqualTo(86400L);
    }

    @Test
    public void loginShouldThrowUnauthorizedExceptionWhenEmailDoesNotExist(){
        when(userRepository.findByEmail("ana@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(loginRequestDTO)).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    public void loginShouldThrowUnauthorizedExceptionWhenDataPasswordIsInvalid(){
        when(userRepository.findByEmail("ana@gmail.com")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(UnauthorizedException.class);

        assertThatThrownBy(() -> userService.login(loginRequestDTO)).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    public void loginShouldThrowRuntimeExceptionWhenJWTCreationFails(){
        when(userRepository.findByEmail("ana@gmail.com")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(JWTCreationException.class);

        assertThatThrownBy(() -> userService.login(loginRequestDTO)).isInstanceOf(RuntimeException.class);
    }

}
