package br.com.senai.lab365.labmedical.services;

import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaRequestDTO;
import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaResponseDTO;
import br.com.senai.lab365.labmedical.entities.ConsultaEntity;
import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.exceptions.exames.ResourceNotFoundException;
import br.com.senai.lab365.labmedical.repositories.ConsultaRepository;
import br.com.senai.lab365.labmedical.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AuthService authService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrMedico = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_MEDICO"));

        Optional<ConsultaEntity> consultaEntityOptional = consultaRepository.findById(id);

        if (consultaEntityOptional.isPresent()) {
            ConsultaEntity consultaEntity = consultaEntityOptional.get();

            if (!isAdminOrMedico) {
                Long pacienteId = authService.getPacienteAutenticadoId();
                if (!consultaEntity.getPaciente().getId().equals(pacienteId)) {
                    throw new AccessDeniedException("Você não tem permissão para acessar esta consulta");
                }
            }

            return Optional.of(new ConsultaResponseDTO(
                    consultaEntity.getId(),
                    consultaEntity.getMotivoConsulta(),
                    consultaEntity.getDataConsulta(),
                    consultaEntity.getHorarioConsulta(),
                    consultaEntity.getDescricaoProblema(),
                    consultaEntity.getMedicacaoReceitada(),
                    consultaEntity.getDosagemPrecaucoes(),
                    consultaEntity.getPaciente().getId()
            ));
        } else {
            throw new ResourceNotFoundException("Consulta não encontrada");
        }
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

    public Page<ConsultaResponseDTO> getAllConsultas(Pageable pageable) {
        return consultaRepository.findAll(pageable)
                .map(consulta -> new ConsultaResponseDTO(
                        consulta.getId(),
                        consulta.getMotivoConsulta(),
                        consulta.getDataConsulta(),
                        consulta.getHorarioConsulta(),
                        consulta.getDescricaoProblema(),
                        consulta.getMedicacaoReceitada(),
                        consulta.getDosagemPrecaucoes(),
                        consulta.getPaciente().getId()));
    }
}