package com.gestao.bizflow_api.exception;

public class EstoqueInsuficienteException extends RuntimeException{
    public EstoqueInsuficienteException(String mensagem){
        super(mensagem);
    }
}
