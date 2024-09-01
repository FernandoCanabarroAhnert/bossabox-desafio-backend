package com.fernandocanabarro.desafio_bossabox_tools.openapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface UserControllerOpenAPI {

    @Operation(
    description = "Registrar Usuário",
    summary = "Endpoint responsável por receber a requisição de Registrar Usuário",
    responses = {
         @ApiResponse(description = "Usuário Registrado", responseCode = "201"),
         @ApiResponse(description = "Algum Campo do Usuário está Inválido, ou o Email já existe", responseCode = "422")
   		}
	)
    ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationRequestDTO dto);


    @Operation(
    description = "Efetuar Login",
    summary = "Endpoint responsável por receber a requisição de Efetuar Login",
    responses = {
        @ApiResponse(description = "Login Efetuado", responseCode = "200"),
         @ApiResponse(description = "Credenciais Inválidas", responseCode = "401"),
   		}
	)
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto);
}
