package br.com.senai.lab365.labmedical.dtos.login;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotNull @Size(max = 255) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Size(max = 255) String email) {
        this.email = email;
    }

    public @NotNull @Size(max = 255) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(max = 255) String password) {
        this.password = password;
    }
}
