package br.com.senai.lab365.labmedical.exceptions.consulta;


public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(String message) {
        super(message);
    }
}