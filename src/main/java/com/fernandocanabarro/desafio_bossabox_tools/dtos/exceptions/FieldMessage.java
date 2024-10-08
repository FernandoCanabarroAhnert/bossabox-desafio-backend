package com.fernandocanabarro.desafio_bossabox_tools.dtos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage {

    private String fieldName;
    private String message;
}
