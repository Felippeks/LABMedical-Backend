package br.com.senai.lab365.labmedical.exceptions.exames;

public class ExameNaoEncontradoException extends RuntimeException {
    public ExameNaoEncontradoException(String message) {
        super(message);
    }
}