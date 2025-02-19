package com.cursospringboot.libraryapi.Exception;

public class CampoInvalido extends RuntimeException {
    private String campo;

    public CampoInvalido(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
