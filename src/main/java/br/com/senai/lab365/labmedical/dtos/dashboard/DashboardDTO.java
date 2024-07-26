package br.com.senai.lab365.labmedical.dtos.dashboard;

public class DashboardDTO {
    private long totalPacientes;
    private long totalConsultas;
    private long totalExames;


    public DashboardDTO() {
    }

    public DashboardDTO(long totalPacientes, long totalConsultas, long totalExames) {
        this.totalPacientes = totalPacientes;
        this.totalConsultas = totalConsultas;
        this.totalExames = totalExames;
    }

    public long getTotalPacientes() {
        return totalPacientes;
    }

    public void setTotalPacientes(long totalPacientes) {
        this.totalPacientes = totalPacientes;
    }

    public long getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(long totalConsultas) {
        this.totalConsultas = totalConsultas;
    }

    public long getTotalExames() {
        return totalExames;
    }

    public void setTotalExames(long totalExames) {
        this.totalExames = totalExames;
    }

}