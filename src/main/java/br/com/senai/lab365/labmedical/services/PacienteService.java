package br.com.senai.lab365.labmedical.services;

import br.com.senai.lab365.labmedical.dtos.paciente.EnderecoDTO;
import br.com.senai.lab365.labmedical.dtos.paciente.PacienteRequestDTO;
import br.com.senai.lab365.labmedical.dtos.paciente.PacienteResponseDTO;
import br.com.senai.lab365.labmedical.entities.Endereco;
import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.exceptions.paciente.CpfJaCadastradoException;
import br.com.senai.lab365.labmedical.exceptions.paciente.PacienteNaoEncontradoException;
import br.com.senai.lab365.labmedical.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.senai.lab365.labmedical.util.ValidarCampoObrigatorio.validarCampoObrigatorio;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public PacienteResponseDTO criarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        PacienteEntity pacienteEntity = convertToEntity(pacienteRequestDTO);
        validarCamposObrigatorios(pacienteEntity);
        verificarCpfDuplicado(pacienteEntity.getCpf());
        PacienteEntity savedEntity = pacienteRepository.save(pacienteEntity);
        return convertToResponseDTO(savedEntity);
    }

    @Transactional
    public List<PacienteResponseDTO> criarPacientesEmLote(List<PacienteRequestDTO> pacientes) {
        List<PacienteResponseDTO> pacientesCriados = new ArrayList<>();
        for (PacienteRequestDTO pacienteRequestDTO : pacientes) {
            PacienteEntity pacienteEntity = convertToEntity(pacienteRequestDTO);
            validarCamposObrigatorios(pacienteEntity);
            verificarCpfDuplicado(pacienteEntity.getCpf());
            PacienteEntity savedEntity = pacienteRepository.save(pacienteEntity);
            pacientesCriados.add(convertToResponseDTO(savedEntity));
        }
        return pacientesCriados;
    }

    private void validarCamposObrigatorios(PacienteEntity pacienteEntity) {
        validarCampoObrigatorio(pacienteEntity.getNomeCompleto(), "nomeCompleto");
        validarCampoObrigatorio(pacienteEntity.getGenero(), "genero");
        validarCampoObrigatorio(pacienteEntity.getDataNascimento(), "dataNascimento");
        validarCampoObrigatorio(pacienteEntity.getCpf(), "cpf");
        validarCampoObrigatorio(pacienteEntity.getRg(), "rg");
        validarCampoObrigatorio(pacienteEntity.getOrgaoExpedidorRg(), "orgaoExpedidorRg");
        validarCampoObrigatorio(pacienteEntity.getEstadoCivil(), "estadoCivil");
        validarCampoObrigatorio(pacienteEntity.getTelefone(), "telefone");
        validarCampoObrigatorio(pacienteEntity.getEmail(), "email");
        validarCampoObrigatorio(pacienteEntity.getNaturalidade(), "naturalidade");
        validarCampoObrigatorio(pacienteEntity.getContatoEmergencia(), "contatoEmergencia");
    }

    private void verificarCpfDuplicado(String cpf) {
        pacienteRepository.findByCpf(cpf)
                .ifPresent(p -> {
                    throw new CpfJaCadastradoException("CPF já cadastrado");
                });
    }

    @Transactional
    public PacienteResponseDTO atualizarPaciente(Long id, PacienteRequestDTO pacienteAtualizado) {
        PacienteEntity paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        updateEntityFromDTO(paciente, pacienteAtualizado);
        PacienteEntity updatedEntity = pacienteRepository.save(paciente);
        return convertToResponseDTO(updatedEntity);
    }

    @Transactional
    public boolean excluirPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new PacienteNaoEncontradoException("Paciente não encontrado para o ID: " + id);
        }
        pacienteRepository.deleteById(id);
        return true;
    }

    public Optional<PacienteResponseDTO> buscarPacientePorId(Long id) {
        return pacienteRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public Page<PacienteResponseDTO> listarPacientes(String nome, String telefone, String email, Pageable pageable) {
        Specification<PacienteEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nomeCompleto")), "%" + nome.toLowerCase() + "%"));
            }
            if (telefone != null && !telefone.isEmpty()) {
                predicates.add(cb.equal(root.get("telefone"), telefone));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(cb.equal(root.get("email"), email));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return pacienteRepository.findAll(spec, pageable).map(this::convertToResponseDTO);
    }

    private PacienteEntity convertToEntity(PacienteRequestDTO dto) {
        PacienteEntity entity = new PacienteEntity();
        entity.setNomeCompleto(dto.getNomeCompleto());
        entity.setGenero(dto.getGenero());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setCpf(dto.getCpf());
        entity.setRg(dto.getRg());
        entity.setOrgaoExpedidorRg(dto.getOrgaoExpedidorRg());
        entity.setEstadoCivil(dto.getEstadoCivil());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setNaturalidade(dto.getNaturalidade());
        entity.setContatoEmergencia(dto.getContatoEmergencia());
        entity.setListaAlergias(dto.getListaAlergias());
        entity.setListaCuidadosEspecificos(dto.getListaCuidadosEspecificos());
        entity.setConvenio(dto.getConvenio());
        entity.setNumeroConvenio(dto.getNumeroConvenio());
        entity.setValidadeConvenio(dto.getValidadeConvenio());
        entity.setEndereco(convertToEntity(dto.getEndereco()));
        return entity;
    }

    private PacienteResponseDTO convertToResponseDTO(PacienteEntity entity) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setGenero(entity.getGenero());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setCpf(entity.getCpf());
        dto.setRg(entity.getRg());
        dto.setOrgaoExpedidorRg(entity.getOrgaoExpedidorRg());
        dto.setEstadoCivil(entity.getEstadoCivil());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        dto.setNaturalidade(entity.getNaturalidade());
        dto.setContatoEmergencia(entity.getContatoEmergencia());
        dto.setListaAlergias(entity.getListaAlergias());
        dto.setListaCuidadosEspecificos(entity.getListaCuidadosEspecificos());
        dto.setConvenio(entity.getConvenio());
        dto.setNumeroConvenio(entity.getNumeroConvenio());
        dto.setValidadeConvenio(entity.getValidadeConvenio());
        dto.setEndereco(convertToDTO(entity.getEndereco()));
        return dto;
    }

    private void updateEntityFromDTO(PacienteEntity entity, PacienteRequestDTO dto) {
        entity.setNomeCompleto(dto.getNomeCompleto());
        entity.setGenero(dto.getGenero());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setCpf(dto.getCpf());
        entity.setRg(dto.getRg());
        entity.setOrgaoExpedidorRg(dto.getOrgaoExpedidorRg());
        entity.setEstadoCivil(dto.getEstadoCivil());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setNaturalidade(dto.getNaturalidade());
        entity.setContatoEmergencia(dto.getContatoEmergencia());
        entity.setListaAlergias(dto.getListaAlergias());
        entity.setListaCuidadosEspecificos(dto.getListaCuidadosEspecificos());
        entity.setConvenio(dto.getConvenio());
        entity.setNumeroConvenio(dto.getNumeroConvenio());
        entity.setValidadeConvenio(dto.getValidadeConvenio());
        entity.setEndereco(convertToEntity(dto.getEndereco()));
    }

    private Endereco convertToEntity(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco entity = new Endereco();
        entity.setCep(dto.getCep());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setPontoDeReferencia(dto.getPontoDeReferencia());
        return entity;
    }

    private EnderecoDTO convertToDTO(Endereco entity) {
        if (entity == null) {
            return null;
        }
        EnderecoDTO dto = new EnderecoDTO();
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setLogradouro(entity.getLogradouro());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setPontoDeReferencia(entity.getPontoDeReferencia());
        return dto;
    }
}