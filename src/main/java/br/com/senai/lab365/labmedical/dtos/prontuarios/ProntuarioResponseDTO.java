package br.com.senai.lab365.labmedical.dtos.prontuarios;

import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaResponseDTO;
import br.com.senai.lab365.labmedical.dtos.exames.ExameResponseDTO;

import java.util.List;

public class ProntuarioResponseDTO {
    private String nomeCompleto;
    private String convenio;
    private String contatoEmergencia;
    private List<String> listaAlergias;
    private List<String> listaCuidadosEspecificos;
    private List<ExameResponseDTO> listaExames;
    private List<ConsultaResponseDTO> listaConsultas;

    public ProntuarioResponseDTO() {
    }

    public ProntuarioResponseDTO(String nomeCompleto, String convenio, String contatoEmergencia, List<String> listaAlergias, List<String> listaCuidadosEspecificos, List<ExameResponseDTO> listaExames, List<ConsultaResponseDTO> listaConsultas) {
        this.nomeCompleto = nomeCompleto;
        this.convenio = convenio;
        this.contatoEmergencia = contatoEmergencia;
        this.listaAlergias = listaAlergias;
        this.listaCuidadosEspecificos = listaCuidadosEspecificos;
        this.listaExames = listaExames;
        this.listaConsultas = listaConsultas;
    }


    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
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
}
