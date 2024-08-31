package com.fernandocanabarro.desafio_bossabox_tools.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationRequestDTO dto){
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){
        return null;
    }
}
