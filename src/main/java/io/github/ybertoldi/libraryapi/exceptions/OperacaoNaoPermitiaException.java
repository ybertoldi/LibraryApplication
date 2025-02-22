package io.github.ybertoldi.libraryapi.exceptions;

public class OperacaoNaoPermitiaException extends  RuntimeException{
    public OperacaoNaoPermitiaException(String mensagem) {
        super(mensagem);
    }
}
