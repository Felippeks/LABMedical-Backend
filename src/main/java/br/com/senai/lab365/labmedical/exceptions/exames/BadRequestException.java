package br.com.senai.lab365.labmedical.exceptions.exames;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}