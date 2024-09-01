package com.fernandocanabarro.desafio_bossabox_tools.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Desafio Backend Bossa Box")
                .version("FernandoCanabarroAhnert")
                .description("Este é um projeto baseado no desafio proposto pela Bossa Box. A aplicação é um simples repositório para gerenciar ferramentas com seus respectivos nomes, links, descrições e tags."))
                .externalDocs(new ExternalDocumentation()
                        .description("Link Notion do Desafio proposto")
                        .url("https://bossabox.notion.site/Back-end-0b2c45f1a00e4a849eefe3b1d57f23c6"));
    }
}
