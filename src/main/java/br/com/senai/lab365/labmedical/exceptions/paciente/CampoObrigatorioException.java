package br.com.senai.lab365.labmedical.exceptions.paciente;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String campo) {
        super("Campo obrigatório faltando: " + campo);
    }
}