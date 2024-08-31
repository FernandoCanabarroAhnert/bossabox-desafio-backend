package com.fernandocanabarro.desafio_bossabox_tools.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.exceptions.FieldMessage;
import com.fernandocanabarro.desafio_bossabox_tools.entities.User;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationRequestDTOValidator implements ConstraintValidator<RegistrationRequestDTOValid, RegistrationRequestDTO>{

    private final UserRepository userRepository;

    @Override
    public void initialize(RegistrationRequestDTOValid ann){}

    @Override
    public boolean isValid(RegistrationRequestDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> errors = new ArrayList<>();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            errors.add(new FieldMessage("email", "Este Email já existe"));
        }

        String password = dto.getPassword();
        if (!Pattern.matches(".*[a-z].*", password)) {
            errors.add(new FieldMessage("password", "Senha deve conter pelo menos uma letra minúscula"));
        }
        if (!Pattern.matches(".*[A-Z].*", password)) {
            errors.add(new FieldMessage("password", "Senha deve conter pelo menos uma letra maiúscula"));
        }
        if (!Pattern.matches(".*[0-9].*", password)) {
            errors.add(new FieldMessage("password", "Senha deve conter pelo menos um número"));
        }
        if (!Pattern.matches(".*[\\W].*", password)) {
            errors.add(new FieldMessage("password", "Senha deve conter pelo menos um caractere especial"));
        }

        for (FieldMessage f : errors){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                .addPropertyNode(f.getFieldName())
                .addConstraintViolation();
        }

        return errors.isEmpty();
    }



}
