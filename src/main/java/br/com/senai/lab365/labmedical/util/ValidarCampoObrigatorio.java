package br.com.senai.lab365.labmedical.util;

import br.com.senai.lab365.labmedical.exceptions.paciente.CampoObrigatorioException;

public class ValidarCampoObrigatorio {

    public static void validarCampoObrigatorio(Object valor, String nomeCampo) {
        boolean isCampoInvalido = valor == null || (valor instanceof String && ((String) valor).isEmpty());
        if (isCampoInvalido) {
            throw new CampoObrigatorioException(nomeCampo);
        }
    }
}