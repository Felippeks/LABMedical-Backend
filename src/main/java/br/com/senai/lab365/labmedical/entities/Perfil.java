package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
    @Schema(description = "Perfil de administrador")
    ADMIN, MEDICO, PACIENTE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}