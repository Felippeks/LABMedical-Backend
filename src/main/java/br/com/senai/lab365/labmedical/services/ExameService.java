package br.com.senai.lab365.labmedical.services;


import br.com.senai.lab365.labmedical.dtos.exames.ExameRequestDTO;
import br.com.senai.lab365.labmedical.dtos.exames.ExameResponseDTO;
import br.com.senai.lab365.labmedical.entities.ExameEntity;
import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.exceptions.exames.ResourceNotFoundException;
import br.com.senai.lab365.labmedical.repositories.ExameRepository;
import br.com.senai.lab365.labmedical.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ExameService {

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AuthService authService; // Injeção do AuthService

    public ExameResponseDTO createExame(ExameRequestDTO exameRequestDTO) {
        Optional<PacienteEntity> pacienteOptional = pacienteRepository.findById(exameRequestDTO.getPacienteId());
        if (!pacienteOptional.isPresent()) {
            throw new ResourceNotFoundException("Paciente não encontrado");
        }
        PacienteEntity paciente = pacienteOptional.get();

        ExameEntity exameEntity = new ExameEntity(
                null,
                exameRequestDTO.getNomeExame(),
                exameRequestDTO.getDataExame(),
                exameRequestDTO.getHorarioExame(),
                exameRequestDTO.getTipoExame(),
                exameRequestDTO.getLaboratorio(),
                exameRequestDTO.getUrlDocumento(),
                exameRequestDTO.getResultados(),
                paciente
        );
        ExameEntity savedExame = exameRepository.save(exameEntity);
        return new ExameResponseDTO(
                savedExame.getId(),
                savedExame.getNomeExame(),
                savedExame.getDataExame(),
                savedExame.getHorarioExame(),
                savedExame.getTipoExame(),
                savedExame.getLaboratorio(),
                savedExame.getUrlDocumento(),
                savedExame.getResultados(),
                savedExame.getPaciente().getId()
        );
    }

    public ExameResponseDTO getExameById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrMedico = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_MEDICO"));

        Optional<ExameEntity> exameEntityOptional = exameRepository.findById(id);

        if (exameEntityOptional.isPresent()) {
            ExameEntity exameEntity = exameEntityOptional.get();

            if (!isAdminOrMedico) {
                Long pacienteId = authService.getPacienteAutenticadoId();
                if (!exameEntity.getPaciente().getId().equals(pacienteId)) {
                    throw new AccessDeniedException("Você não tem permissão para acessar este exame");
                }
            }

            return new ExameResponseDTO(
                    exameEntity.getId(),
                    exameEntity.getNomeExame(),
                    exameEntity.getDataExame(),
                    exameEntity.getHorarioExame(),
                    exameEntity.getTipoExame(),
                    exameEntity.getLaboratorio(),
                    exameEntity.getUrlDocumento(),
                    exameEntity.getResultados(),
                    exameEntity.getPaciente().getId()
            );
        } else {
            throw new ResourceNotFoundException("Exame não encontrado");
        }
    }

    public ExameResponseDTO updateExame(Long id, ExameRequestDTO exameRequestDTO) {
        Optional<ExameEntity> exameEntityOptional = exameRepository.findById(id);
        if (exameEntityOptional.isPresent()) {
            ExameEntity exameEntity = exameEntityOptional.get();
            Optional<PacienteEntity> pacienteOptional = pacienteRepository.findById(exameRequestDTO.getPacienteId());
            if (!pacienteOptional.isPresent()) {
                throw new ResourceNotFoundException("Paciente não encontrado");
            }
            PacienteEntity paciente = pacienteOptional.get();

            exameEntity.setNomeExame(exameRequestDTO.getNomeExame());
            exameEntity.setDataExame(exameRequestDTO.getDataExame());
            exameEntity.setHorarioExame(exameRequestDTO.getHorarioExame());
            exameEntity.setTipoExame(exameRequestDTO.getTipoExame());
            exameEntity.setLaboratorio(exameRequestDTO.getLaboratorio());
            exameEntity.setUrlDocumento(exameRequestDTO.getUrlDocumento());
            exameEntity.setResultados(exameRequestDTO.getResultados());
            exameEntity.setPaciente(paciente);
            ExameEntity updatedExame = exameRepository.save(exameEntity);
            return new ExameResponseDTO(
                    updatedExame.getId(),
                    updatedExame.getNomeExame(),
                    updatedExame.getDataExame(),
                    updatedExame.getHorarioExame(),
                    updatedExame.getTipoExame(),
                    updatedExame.getLaboratorio(),
                    updatedExame.getUrlDocumento(),
                    updatedExame.getResultados(),
                    updatedExame.getPaciente().getId()
            );
        } else {
            throw new ResourceNotFoundException("Exame não encontrado");
        }
    }

    public Page<ExameResponseDTO> getAllExames(Pageable pageable) {
        return exameRepository.findAll(pageable)
                .map(exame -> new ExameResponseDTO(
                        exame.getId(),
                        exame.getNomeExame(),
                        exame.getDataExame(),
                        exame.getHorarioExame(),
                        exame.getTipoExame(),
                        exame.getLaboratorio(),
                        exame.getUrlDocumento(),
                        exame.getResultados(),
                        exame.getPaciente().getId()));
    }

    public void deleteExame(Long id) {
        if (exameRepository.existsById(id)) {
            exameRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Exame não encontrado");
        }
    }
}