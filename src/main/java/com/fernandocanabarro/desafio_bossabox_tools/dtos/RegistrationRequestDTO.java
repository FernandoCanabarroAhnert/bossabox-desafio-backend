package com.fernandocanabarro.desafio_bossabox_tools.dtos;

import com.fernandocanabarro.desafio_bossabox_tools.services.validations.RegistrationRequestDTOValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RegistrationRequestDTOValid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotBlank(message = "Campo Requerido")
    private String fullName;

    @NotBlank(message = "Campo Requerido")
    private String email;

    @Size(min = 8,message = "Senha deve conter pelo menos 8 caracteres")
    private String password;
}
