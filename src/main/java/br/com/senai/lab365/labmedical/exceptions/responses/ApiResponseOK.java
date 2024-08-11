package br.com.senai.lab365.labmedical.exceptions.responses;

public class ApiResponseOK<T> {
    private String mensagem;
    private T dados;

    public ApiResponseOK(String mensagem, T dados) {
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}