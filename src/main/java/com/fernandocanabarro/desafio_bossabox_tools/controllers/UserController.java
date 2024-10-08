package com.fernandocanabarro.desafio_bossabox_tools.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;
import com.fernandocanabarro.desafio_bossabox_tools.openapi.UserControllerOpenAPI;
import com.fernandocanabarro.desafio_bossabox_tools.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerOpenAPI{

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationRequestDTO dto){
        UserDTO obj = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){
        LoginResponseDTO obj = userService.login(dto);
        return ResponseEntity.ok(obj);
    }
}
