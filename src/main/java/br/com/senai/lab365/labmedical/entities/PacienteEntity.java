package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
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
    @Schema(description = "Nome completo do paciente", example = "João da Silva")
    private String nomeCompleto;

    @NotBlank
    @Schema(description = "Gênero do paciente", example = "Masculino")
    private String genero;

    @NotNull
    @Schema(description = "Data de nascimento do paciente", example = "2000-01-01")
    private LocalDate dataNascimento;

    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    private String cpf;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "RG do paciente", example = "123456789")
    private String rg;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "Órgão expedidor do RG do paciente", example = "SSP-SP")
    private String orgaoExpedidorRg;

    @NotBlank
    @Schema(description = "Estado civil do paciente", example = "Solteiro")
    private String estadoCivil;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}")
    @Schema(description = "Telefone do paciente", example = "(11) 9 1234-5678")
    private String telefone;

    @Email
    @Schema(description = "E-mail do paciente", example = "email@email.com")
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Naturalidade do paciente", example = "São Paulo")
    private String naturalidade;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}")
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


    public PacienteEntity() {
    }

    public PacienteEntity(Long id, String nomeCompleto, String genero, LocalDate dataNascimento, String cpf, String rg, String orgaoExpedidorRg, String estadoCivil, String telefone, String email, String naturalidade, String contatoEmergencia, List<String> listaAlergias, List<String> listaCuidadosEspecificos, String convenio, String numeroConvenio, LocalDate validadeConvenio, Endereco endereco) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.rg = rg;
        this.orgaoExpedidorRg = orgaoExpedidorRg;
        this.estadoCivil = estadoCivil;
        this.telefone = telefone;
        this.email = email;
        this.naturalidade = naturalidade;
        this.contatoEmergencia = contatoEmergencia;
        this.listaAlergias = listaAlergias;
        this.listaCuidadosEspecificos = listaCuidadosEspecificos;
        this.convenio = convenio;
        this.numeroConvenio = numeroConvenio;
        this.validadeConvenio = validadeConvenio;
        this.endereco = endereco;
    }
}
