package br.com.senai.lab365.labmedical.dtos.cadastros;

import br.com.senai.lab365.labmedical.entities.Perfil;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CadastroRequestDTO {

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    @Size(max = 14)
    private String cpf;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    private Perfil perfil;

    public CadastroRequestDTO() {
    }

    public CadastroRequestDTO(String nome, String email, LocalDate dataNascimento, String cpf, String password, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.password = password;
        this.perfil = perfil;
    }

    public @NotNull @Size(max = 255) String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 255) String nome) {
        this.nome = nome;
    }

    public @NotNull @Email @Size(max = 255) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email @Size(max = 255) String email) {
        this.email = email;
    }

    public @NotNull LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@NotNull LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @NotNull @Size(max = 14) String getCpf() {
        return cpf;
    }

    public void setCpf(@NotNull @Size(max = 14) String cpf) {
        this.cpf = cpf;
    }

    public @NotNull @Size(max = 255) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(max = 255) String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}

