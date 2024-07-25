package br.com.senai.lab365.labmedical.services;

import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaRequestDTO;
import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaResponseDTO;
import br.com.senai.lab365.labmedical.entities.ConsultaEntity;
import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.repositories.ConsultaRepository;
import br.com.senai.lab365.labmedical.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public ConsultaResponseDTO createConsulta(ConsultaRequestDTO consultaRequestDTO) {
        ConsultaEntity consulta = new ConsultaEntity();
        consulta.setMotivoConsulta(consultaRequestDTO.getMotivoConsulta());
        consulta.setDataConsulta(consultaRequestDTO.getDataConsulta());
        consulta.setHorarioConsulta(consultaRequestDTO.getHorarioConsulta());
        consulta.setDescricaoProblema(consultaRequestDTO.getDescricaoProblema());
        consulta.setMedicacaoReceitada(consultaRequestDTO.getMedicacaoReceitada());
        consulta.setDosagemPrecaucoes(consultaRequestDTO.getDosagemPrecaucoes());

        PacienteEntity paciente = pacienteRepository.findById(consultaRequestDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        consulta.setPaciente(paciente);

        ConsultaEntity savedConsulta = consultaRepository.save(consulta);
        return new ConsultaResponseDTO(savedConsulta.getId(), savedConsulta.getMotivoConsulta(), savedConsulta.getDataConsulta(), savedConsulta.getHorarioConsulta(), savedConsulta.getDescricaoProblema(), savedConsulta.getMedicacaoReceitada(), savedConsulta.getDosagemPrecaucoes(), savedConsulta.getPaciente().getId());
    }

    public Optional<ConsultaResponseDTO> getConsultaById(Long id) {
        return consultaRepository.findById(id).map(consulta -> new ConsultaResponseDTO(consulta.getId(), consulta.getMotivoConsulta(), consulta.getDataConsulta(), consulta.getHorarioConsulta(), consulta.getDescricaoProblema(), consulta.getMedicacaoReceitada(), consulta.getDosagemPrecaucoes(), consulta.getPaciente().getId()));
    }

    public ConsultaResponseDTO updateConsulta(Long id, ConsultaRequestDTO consultaRequestDTO) {
        ConsultaEntity consulta = consultaRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        consulta.setMotivoConsulta(consultaRequestDTO.getMotivoConsulta());
        consulta.setDataConsulta(consultaRequestDTO.getDataConsulta());
        consulta.setHorarioConsulta(consultaRequestDTO.getHorarioConsulta());
        consulta.setDescricaoProblema(consultaRequestDTO.getDescricaoProblema());
        consulta.setMedicacaoReceitada(consultaRequestDTO.getMedicacaoReceitada());
        consulta.setDosagemPrecaucoes(consultaRequestDTO.getDosagemPrecaucoes());

        PacienteEntity paciente = pacienteRepository.findById(consultaRequestDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        consulta.setPaciente(paciente);

        ConsultaEntity updatedConsulta = consultaRepository.save(consulta);
        return new ConsultaResponseDTO(updatedConsulta.getId(), updatedConsulta.getMotivoConsulta(), updatedConsulta.getDataConsulta(), updatedConsulta.getHorarioConsulta(), updatedConsulta.getDescricaoProblema(), updatedConsulta.getMedicacaoReceitada(), updatedConsulta.getDosagemPrecaucoes(), updatedConsulta.getPaciente().getId());
    }

    public void deleteConsulta(Long id) {
        ConsultaEntity consulta = consultaRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        consultaRepository.delete(consulta);
    }

    public List<ConsultaResponseDTO> getConsultasByMotivo(String motivoConsulta) {
        return consultaRepository.findByMotivoConsulta(motivoConsulta).stream().map(consulta -> new ConsultaResponseDTO(consulta.getId(), consulta.getMotivoConsulta(), consulta.getDataConsulta(), consulta.getHorarioConsulta(), consulta.getDescricaoProblema(), consulta.getMedicacaoReceitada(), consulta.getDosagemPrecaucoes(), consulta.getPaciente().getId())).collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> getAllConsultas() {
        return consultaRepository.findAll().stream()
                .map(consulta -> new ConsultaResponseDTO(
                        consulta.getId(),
                        consulta.getMotivoConsulta(),
                        consulta.getDataConsulta(),
                        consulta.getHorarioConsulta(),
                        consulta.getDescricaoProblema(),
                        consulta.getMedicacaoReceitada(),
                        consulta.getDosagemPrecaucoes(),
                        consulta.getPaciente().getId()))
                .collect(Collectors.toList());
    }
}