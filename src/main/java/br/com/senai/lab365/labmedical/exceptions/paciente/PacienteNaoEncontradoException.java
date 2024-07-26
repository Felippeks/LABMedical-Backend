package br.com.senai.lab365.labmedical.exceptions.paciente;

public class PacienteNaoEncontradoException extends RuntimeException {
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
