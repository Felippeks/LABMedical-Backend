package br.com.senai.lab365.labmedical.services;


import br.com.senai.lab365.labmedical.dtos.dashboard.DashboardDTO;
import br.com.senai.lab365.labmedical.repositories.ConsultaRepository;
import br.com.senai.lab365.labmedical.repositories.ExameRepository;
import br.com.senai.lab365.labmedical.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ExameRepository exameRepository;

    public DashboardDTO getDashboardStatistics() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setTotalPacientes(pacienteRepository.count());
        dashboardDTO.setTotalConsultas(consultaRepository.count());
        dashboardDTO.setTotalExames(exameRepository.count());
        return dashboardDTO;
    }
}