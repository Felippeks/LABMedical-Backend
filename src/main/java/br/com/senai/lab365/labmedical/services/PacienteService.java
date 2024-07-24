package br.com.senai.lab365.labmedical.services;

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
    public PacienteEntity criarPaciente(PacienteEntity pacienteEntity) {

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

        pacienteRepository.findByCpf(pacienteEntity.getCpf())
                .ifPresent(p -> {
                    throw new CpfJaCadastradoException("CPF já cadastrado");
                });

        return pacienteRepository.save(pacienteEntity);
    }

    @Transactional
    public PacienteEntity atualizarPaciente(Long id, PacienteEntity pacienteAtualizado) {
        PacienteEntity paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        paciente.setNomeCompleto(pacienteAtualizado.getNomeCompleto());
        paciente.setGenero(pacienteAtualizado.getGenero());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        paciente.setRg(pacienteAtualizado.getRg());
        paciente.setOrgaoExpedidorRg(pacienteAtualizado.getOrgaoExpedidorRg());
        paciente.setEstadoCivil(pacienteAtualizado.getEstadoCivil());
        paciente.setTelefone(pacienteAtualizado.getTelefone());
        paciente.setEmail(pacienteAtualizado.getEmail());
        paciente.setNaturalidade(pacienteAtualizado.getNaturalidade());
        paciente.setContatoEmergencia(pacienteAtualizado.getContatoEmergencia());
        paciente.setListaAlergias(pacienteAtualizado.getListaAlergias());
        paciente.setListaCuidadosEspecificos(pacienteAtualizado.getListaCuidadosEspecificos());
        paciente.setConvenio(pacienteAtualizado.getConvenio());
        paciente.setNumeroConvenio(pacienteAtualizado.getNumeroConvenio());
        paciente.setValidadeConvenio(pacienteAtualizado.getValidadeConvenio());
        paciente.setEndereco(pacienteAtualizado.getEndereco());

        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void excluirPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new PacienteNaoEncontradoException("Paciente não encontrado para o ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public Optional<PacienteEntity> buscarPacientePorId(Long id) {
        return Optional.ofNullable(pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado para o ID: " + id)));
    }

    public Page<PacienteEntity> listarPacientes(String nome, String telefone, String email, Pageable pageable) {
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

        return pacienteRepository.findAll(spec, pageable);
    }
}