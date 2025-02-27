package io.github.ybertoldi.libraryapi.exceptions;

public class CampoInvalidoException extends RuntimeException{
    String campo;
    String mensagem;

    public CampoInvalidoException(String campo, String mensagem) {
        super("campo preenchido incorretamente");
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
