package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consultas")
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    @Schema(description = "Identificador único da consulta", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 8, max = 64)
    @Column(name = "motivo_consulta", length = 64, nullable = false)
    @Schema(description = "Motivo da consulta", example = "Dor de cabeça")
    private String motivoConsulta;

    @NotNull
    @Column(name = "data_consulta", columnDefinition = "DATE", nullable = false)
    @Schema(description = "Data da consulta", example = "2021-12-31")
    private LocalDate dataConsulta;

    @NotNull
    @Column(name = "horario_consulta", columnDefinition = "TIME", nullable = false)
    @Schema(description = "Horário da consulta", example = "08:00")
    private LocalTime horarioConsulta;

    @NotBlank
    @Size(min = 16, max = 1024)
    @Column(name = "descricao_problema", length = 1024,nullable = false)
    @Schema(description = "Descrição do problema", example = "Dor de cabeça intensa")
    private String descricaoProblema;

    @Column(name = "medicacao_receitada", length = 256)
    @Schema(description = "Medicação receitada", example = "Dipirona 1 comprimido a cada 6 horas")
    private String medicacaoReceitada;

    @Size(min = 16, max = 256)
    @Column(name = "dosagem_precaucoes", length = 256)
    @Schema(description = "Dosagem e precauções da medicação", example = "1 comprimido a cada 6 horas")
    private String dosagemPrecaucoes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @Schema(description = "Paciente da consulta")
    private PacienteEntity paciente;

    public ConsultaEntity() {
    }

    public ConsultaEntity(Long id, String motivoConsulta, LocalDate dataConsulta, LocalTime horarioConsulta, String descricaoProblema, String medicacaoReceitada, String dosagemPrecaucoes, PacienteEntity paciente) {
        this.id = id;
        this.motivoConsulta = motivoConsulta;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
        this.descricaoProblema = descricaoProblema;
        this.medicacaoReceitada = medicacaoReceitada;
        this.dosagemPrecaucoes = dosagemPrecaucoes;
        this.paciente = paciente;
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

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
}