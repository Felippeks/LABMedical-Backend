package br.com.senai.lab365.labmedical.dtos.consultas;


import jakarta.validation.constraints.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaRequestDTO {

    @NotBlank(message = "Motivo da consulta é obrigatório")
    @javax.validation.constraints.Size(min = 8, max = 64, message = "Motivo da consulta deve ter entre 8 e 64 caracteres")
    private String motivoConsulta;

    @javax.validation.constraints.NotNull(message = "Data da consulta é obrigatória")
    private LocalDate dataConsulta;

    @javax.validation.constraints.NotNull(message = "Horário da consulta é obrigatório")
    private LocalTime horarioConsulta;

    @NotBlank(message = "Descrição do problema é obrigatória")
    @javax.validation.constraints.Size(min = 16, max = 1024, message = "Descrição do problema deve ter entre 16 e 1024 caracteres")
    private String descricaoProblema;

    private String medicacaoReceitada;

    @javax.validation.constraints.Size(min = 16, max = 256, message = "Dosagem e precauções deve ter entre 16 e 256 caracteres")
    private String dosagemPrecaucoes;

    @NotNull(message = "Id do paciente é obrigatório")
    private Long pacienteId;

    public ConsultaRequestDTO() {
    }

    public ConsultaRequestDTO(String motivoConsulta, LocalDate dataConsulta, LocalTime horarioConsulta, String descricaoProblema, String medicacaoReceitada, String dosagemPrecaucoes, Long pacienteId) {
        this.motivoConsulta = motivoConsulta;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
        this.descricaoProblema = descricaoProblema;
        this.medicacaoReceitada = medicacaoReceitada;
        this.dosagemPrecaucoes = dosagemPrecaucoes;
        this.pacienteId = pacienteId;
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