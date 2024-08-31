package com.fernandocanabarro.desafio_bossabox_tools.services.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String msg){
        super(msg);
    }
}
