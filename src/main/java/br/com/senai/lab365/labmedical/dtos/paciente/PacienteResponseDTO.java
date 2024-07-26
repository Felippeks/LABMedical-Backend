package br.com.senai.lab365.labmedical.dtos.paciente;

import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaResponseDTO;
import br.com.senai.lab365.labmedical.dtos.exames.ExameResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class PacienteResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String genero;
    private LocalDate dataNascimento;
    private String cpf;
    private String rg;
    private String orgaoExpedidorRg;
    private String estadoCivil;
    private String telefone;
    private String email;
    private String naturalidade;
    private String contatoEmergencia;
    private List<String> listaAlergias;
    private List<String> listaCuidadosEspecificos;
    private String convenio;
    private String numeroConvenio;
    private LocalDate validadeConvenio;
    private EnderecoDTO endereco;
    private List<ExameResponseDTO> listaExames;
    private List<ConsultaResponseDTO> listaConsultas;

    public List<ExameResponseDTO> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<ExameResponseDTO> listaExames) {
        this.listaExames = listaExames;
    }

    public List<ConsultaResponseDTO> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(List<ConsultaResponseDTO> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoExpedidorRg() {
        return orgaoExpedidorRg;
    }

    public void setOrgaoExpedidorRg(String orgaoExpedidorRg) {
        this.orgaoExpedidorRg = orgaoExpedidorRg;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(String contatoEmergencia) {
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }


    public PacienteResponseDTO() {
    }

    public PacienteResponseDTO(Long id, String nomeCompleto, String genero, LocalDate dataNascimento, String cpf, String rg, String orgaoExpedidorRg, String estadoCivil, String telefone, String email, String naturalidade, String contatoEmergencia, List<String> listaAlergias, List<String> listaCuidadosEspecificos, String convenio, String numeroConvenio, LocalDate validadeConvenio, EnderecoDTO endereco, List<ExameResponseDTO> listaExames, List<ConsultaResponseDTO> listaConsultas) {
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
        this.listaExames = listaExames;
        this.listaConsultas = listaConsultas;
    }
}