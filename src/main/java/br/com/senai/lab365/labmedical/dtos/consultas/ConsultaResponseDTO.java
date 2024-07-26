package br.com.senai.lab365.labmedical.dtos.consultas;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaResponseDTO {

    private Long id;
    private String motivoConsulta;
    private LocalDate dataConsulta;
    private LocalTime horarioConsulta;
    private String descricaoProblema;
    private String medicacaoReceitada;
    private String dosagemPrecaucoes;
    private Long pacienteId;



    public ConsultaResponseDTO() {
    }

    public ConsultaResponseDTO(Long id, String motivoConsulta, LocalDate dataConsulta, LocalTime horarioConsulta, String descricaoProblema, String medicacaoReceitada, String dosagemPrecaucoes, Long pacienteId) {
        this.id = id;
        this.motivoConsulta = motivoConsulta;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
        this.descricaoProblema = descricaoProblema;
        this.medicacaoReceitada = medicacaoReceitada;
        this.dosagemPrecaucoes = dosagemPrecaucoes;
        this.pacienteId = pacienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(LocalTime horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getMedicacaoReceitada() {
        return medicacaoReceitada;
    }

    public void setMedicacaoReceitada(String medicacaoReceitada) {
        this.medicacaoReceitada = medicacaoReceitada;
    }

    public String getDosagemPrecaucoes() {
        return dosagemPrecaucoes;
    }

    public void setDosagemPrecaucoes(String dosagemPrecaucoes) {
        this.dosagemPrecaucoes = dosagemPrecaucoes;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}