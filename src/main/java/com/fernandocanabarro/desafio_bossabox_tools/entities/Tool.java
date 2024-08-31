package com.fernandocanabarro.desafio_bossabox_tools.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "tools")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    @Id
    private String id;
    private String title;
    private String link;
    private String description;
    private List<String> tags = new ArrayList<>();

}
