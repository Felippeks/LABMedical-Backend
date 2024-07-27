package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(description = "Identificador do usuário", example = "1")
    private Long id;

    @Column(nullable = false, length = 255)
    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String nome;


    @Column(nullable = false, unique = true, length = 255)
    @Schema(description = "E-mail do usuário", example = "email@email.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 14)
    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Column(nullable = false, length = 255)
    @Schema(description = "Senha do usuário", example = "123456")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Perfil do usuário", example = "PACIENTE")
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario")
    @JoinColumn(name = "paciente_id")
    private PacienteEntity paciente;

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long id, String nome, String email, LocalDate dataNascimento, String cpf, String password, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.password = password;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(perfil.getAuthority()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}