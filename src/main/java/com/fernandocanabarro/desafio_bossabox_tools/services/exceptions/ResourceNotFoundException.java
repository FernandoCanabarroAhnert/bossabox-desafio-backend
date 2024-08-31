package com.fernandocanabarro.desafio_bossabox_tools.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String id){
        super("Recurso não encontrado! Id = " + id);
    }
}
