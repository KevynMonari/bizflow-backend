package com.gestao.bizflow_api.exception;

public class EntidadeNaoEncontradaException extends RuntimeException{
    public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
