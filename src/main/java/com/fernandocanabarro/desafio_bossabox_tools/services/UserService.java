package com.fernandocanabarro.desafio_bossabox_tools.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public UserDTO register(RegistrationRequestDTO dto){
        return null;
    }

    private void toEntity(User user, RegistrationRequestDTO dto) {
        return;
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto){
        return null;
    }
}
