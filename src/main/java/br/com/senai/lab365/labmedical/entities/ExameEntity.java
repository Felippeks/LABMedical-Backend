package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exames")
public class ExameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do exame", example = "1")
    private Long id;

    @NotBlank(message = "Nome do exame é obrigatório")
    @Size(min = 8, max = 64, message = "Nome do exame deve ter entre 8 e 64 caracteres")
    @Column(name = "nome_exame", nullable = false, length = 64)
    @Schema(description = "Nome do exame", example = "Hemograma")
    private String nomeExame;

    @NotNull(message = "Data do exame é obrigatória")
    @Column(name = "data_exame", nullable = false)
    @Schema(description = "Data do exame", example = "2000-01-01")
    private LocalDate dataExame;

    @NotNull(message = "Horário do exame é obrigatório")
    @Column(name = "horario_exame", nullable = false)
    @Schema(description = "Horário do exame", example = "08:00")
    private LocalTime horarioExame;

    @NotBlank(message = "Tipo do exame é obrigatório")
    @Size(min = 4, max = 32, message = "Tipo do exame deve ter entre 4 e 32 caracteres")
    @Column(name = "tipo_exame", nullable = false, length = 32)
    @Schema(description = "Tipo do exame", example = "Sangue")
    private String tipoExame;

    @NotBlank(message = "Laboratório é obrigatório")
    @Size(min = 4, max = 32, message = "Laboratório deve ter entre 4 e 32 caracteres")
    @Column(name = "laboratorio", nullable = false, length = 32)
    @Schema(description = "Laboratório do exame", example = "Laboratório São Lucas")
    private String laboratorio;

    @Column(name = "url_documento")
    @Schema(description = "URL do documento do exame", example = "http://www.laboratoriosaolucas.com.br/exame.pdf")
    private String urlDocumento;

    @Size(min = 16, max = 1024, message = "Resultados devem ter entre 16 e 1024 caracteres")
    @Column(name = "resultados", length = 1024)
    @Schema(description = "Resultados do exame", example = "Hemácias: 5.000.000")
    private String resultados;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Paciente do exame")
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity paciente;

    public ExameEntity() {
    }

    public ExameEntity(Long id, String nomeExame, LocalDate dataExame, LocalTime horarioExame, String tipoExame, String laboratorio, String urlDocumento, String resultados, PacienteEntity paciente) {
        this.id = id;
        this.nomeExame = nomeExame;
        this.dataExame = dataExame;
        this.horarioExame = horarioExame;
        this.tipoExame = tipoExame;
        this.laboratorio = laboratorio;
        this.urlDocumento = urlDocumento;
        this.resultados = resultados;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
}