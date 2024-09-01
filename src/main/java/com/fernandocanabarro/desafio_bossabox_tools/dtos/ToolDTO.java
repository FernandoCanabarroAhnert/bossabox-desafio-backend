package com.fernandocanabarro.desafio_bossabox_tools.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToolDTO extends RepresentationModel<ToolDTO>{

    private String id;
    @NotBlank(message = "Campo Requerido")
    private String title;
    @NotBlank(message = "Campo Requerido")
    private String link;
    @NotBlank(message = "Campo Requerido")
    private String description;
    @NotEmpty(message = "Deve haver pelo menos uma Tag")
    private List<String> tags = new ArrayList<>();

    public ToolDTO(Tool entity){
        id = entity.getId();
        title = entity.getTitle();
        link = entity.getLink();
        description = entity.getDescription();
        tags = entity.getTags();
    }
}
