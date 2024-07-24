package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<?> criarPaciente(@RequestBody PacienteEntity pacienteEntity) {
        PacienteEntity paciente = pacienteService.criarPaciente(pacienteEntity);
        return ResponseEntity.status(201).body(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<PacienteEntity>> listarPacientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        Page<PacienteEntity> pacientes = pacienteService.listarPacientes(nome, telefone, email, pageable);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteEntity> atualizarPaciente(@PathVariable Long id, @RequestBody PacienteEntity pacienteAtualizado) {
        PacienteEntity paciente = pacienteService.atualizarPaciente(id, pacienteAtualizado);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteEntity> buscarPacientePorId(@PathVariable Long id) {
        Optional<PacienteEntity> paciente = pacienteService.buscarPacientePorId(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}