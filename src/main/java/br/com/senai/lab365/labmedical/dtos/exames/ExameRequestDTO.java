package br.com.senai.lab365.labmedical.dtos.exames;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExameRequestDTO {

    private String nomeExame;
    private LocalDate dataExame;
    private LocalTime horarioExame;
    private String tipoExame;
    private String laboratorio;
    private String urlDocumento;
    private String resultados;
    private Long pacienteId;


    public ExameRequestDTO() {
    }

    public ExameRequestDTO(String nomeExame, LocalDate dataExame, LocalTime horarioExame, String tipoExame, String laboratorio, String urlDocumento, String resultados, Long pacienteId) {
        this.nomeExame = nomeExame;
        this.dataExame = dataExame;
        this.horarioExame = horarioExame;
        this.tipoExame = tipoExame;
        this.laboratorio = laboratorio;
        this.urlDocumento = urlDocumento;
        this.resultados = resultados;
        this.pacienteId = pacienteId;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public LocalDate getDataExame() {
        return dataExame;
    }

    public void setDataExame(LocalDate dataExame) {
        this.dataExame = dataExame;
    }

    public LocalTime getHorarioExame() {
        return horarioExame;
    }

    public void setHorarioExame(LocalTime horarioExame) {
        this.horarioExame = horarioExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}