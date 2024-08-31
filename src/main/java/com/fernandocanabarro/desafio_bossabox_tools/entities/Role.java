package com.fernandocanabarro.desafio_bossabox_tools.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority{

    private String id;
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
