package com.fernandocanabarro.desafio_bossabox_tools.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fernandocanabarro.desafio_bossabox_tools.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String fullName;
    private String email;
    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public UserDTO(User entity){
        id = entity.getId();
        fullName = entity.getFullName();
        email = entity.getEmail();
        roles = entity.getRoles().stream().map(RoleDTO::new).toList();
    }
}
