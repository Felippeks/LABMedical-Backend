package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Schema(description = "Representa um paciente no sistema")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do paciente", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 8, max = 64)
    @Column(name = "nome_completo", length = 64, nullable = false)
    @Schema(description = "Nome completo do paciente", example = "João da Silva")
    private String nomeCompleto;

    @NotBlank
    @Column(nullable = false, length = 20)
    @Schema(description = "Gênero do paciente", example = "Masculino")
    private String genero;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    @Schema(description = "Data de nascimento do paciente", example = "2000-01-01")
    private LocalDate dataNascimento;

    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    @Column(nullable = false, length = 14)
    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    private String cpf;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    @Schema(description = "RG do paciente", example = "123456789")
    private String rg;

    @NotBlank
    @Size(max = 20)
    @Column(name = "orgao_expedidor_rg", nullable = false, length = 20)
    @Schema(description = "Órgão expedidor do RG do paciente", example = "SSP-SP")
    private String orgaoExpedidorRg;

    @NotBlank
    @Column(name = "estado_civil", nullable = false, length = 20)
    @Schema(description = "Estado civil do paciente", example = "Solteiro")
    private String estadoCivil;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}")
    @Column(nullable = false, length = 20)
    @Schema(description = "Telefone do paciente", example = "(11) 9 1234-5678")
    private String telefone;

    @Email
    @Column(length = 64)
    @Schema(description = "E-mail do paciente", example = "email@email.com")
    private String email;

    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(min = 8, max = 64)
    @Schema(description = "Naturalidade do paciente", example = "São Paulo")
    private String naturalidade;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}")
    @Column(name = "contato_emergencia", nullable = false, length = 20)
    @Schema(description = "Contato de emergência do paciente", example = "(11) 9 1234-5678")
    private String contatoEmergencia;

    @ElementCollection
    @Schema(description = "Lista de alergias do paciente", example = "[\"Alergia 1\", \"Alergia 2\"]")
    private List<String> listaAlergias;

    @ElementCollection
    @Schema(description = "Lista de cuidados específicos do paciente", example = "[\"Cuidado 1\", \"Cuidado 2\"]")
    private List<String> listaCuidadosEspecificos;

    @Schema(description = "Convênio do paciente", example = "Unimed")
    private String convenio;

    @Schema(description = "Número do convênio do paciente", example = "123456")
    private String numeroConvenio;

    @Schema(description = "Validade do convênio do paciente", example = "2022-01-01")
    private LocalDate validadeConvenio;

    @Schema(description = "Endereço do paciente")
    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de exames do paciente")
    private List<ExameEntity> exames = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de consultas do paciente")
    private List<ConsultaEntity> consultas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "Identificador do usuário de acesso do paciente", example = "1")
    private UsuarioEntity usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 8, max = 64) String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(@NotBlank @Size(min = 8, max = 64) String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public @NotBlank String getGenero() {
        return genero;
    }

    public void setGenero(@NotBlank String genero) {
        this.genero = genero;
    }

    public @NotNull LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@NotNull LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @NotBlank @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank @Size(max = 20) String getRg() {
        return rg;
    }

    public void setRg(@NotBlank @Size(max = 20) String rg) {
        this.rg = rg;
    }

    public @NotBlank @Size(max = 20) String getOrgaoExpedidorRg() {
        return orgaoExpedidorRg;
    }

    public void setOrgaoExpedidorRg(@NotBlank @Size(max = 20) String orgaoExpedidorRg) {
        this.orgaoExpedidorRg = orgaoExpedidorRg;
    }

    public @NotBlank String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(@NotBlank String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public @NotBlank @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@NotBlank @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}") String telefone) {
        this.telefone = telefone;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, max = 64) String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(@NotBlank @Size(min = 8, max = 64) String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public @NotBlank @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}") String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(@NotBlank @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}") String contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public List<String> getListaAlergias() {
        return listaAlergias;
    }

    public void setListaAlergias(List<String> listaAlergias) {
        this.listaAlergias = listaAlergias;
    }

    public List<String> getListaCuidadosEspecificos() {
        return listaCuidadosEspecificos;
    }

    public void setListaCuidadosEspecificos(List<String> listaCuidadosEspecificos) {
        this.listaCuidadosEspecificos = listaCuidadosEspecificos;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }

    public LocalDate getValidadeConvenio() {
        return validadeConvenio;
    }

    public void setValidadeConvenio(LocalDate validadeConvenio) {
        this.validadeConvenio = validadeConvenio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<ConsultaEntity> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaEntity> consultas) {
        this.consultas = consultas;
    }

    public List<ExameEntity> getExames() {
        return exames;
    }

    public void setExames(List<ExameEntity> exames) {
        this.exames = exames;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public PacienteEntity() {
    }
}