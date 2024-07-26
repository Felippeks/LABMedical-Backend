package br.com.senai.lab365.labmedical.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
    ADMIN, MEDICO, PACIENTE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}