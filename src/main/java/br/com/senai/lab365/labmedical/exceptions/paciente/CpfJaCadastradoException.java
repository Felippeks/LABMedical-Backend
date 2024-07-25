package br.com.senai.lab365.labmedical.exceptions.paciente;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}