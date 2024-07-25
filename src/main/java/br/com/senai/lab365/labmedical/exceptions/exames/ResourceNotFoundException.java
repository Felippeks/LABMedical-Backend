package br.com.senai.lab365.labmedical.exceptions.exames;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
