package br.com.senai.lab365.labmedical.exceptions.jwt;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}