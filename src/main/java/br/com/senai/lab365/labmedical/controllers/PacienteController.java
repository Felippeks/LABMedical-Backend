package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import br.com.senai.lab365.labmedical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo paciente", responses = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteEntity.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")})
    public ResponseEntity<?> criarPaciente(@RequestBody PacienteEntity pacienteEntity) {
        PacienteEntity paciente = pacienteService.criarPaciente(pacienteEntity);
        return ResponseEntity.status(201).body(paciente);
    }

    @GetMapping
    @Operation(summary = "Lista pacientes com filtros opcionais", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))})
    public ResponseEntity<Page<PacienteEntity>> listarPacientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        Page<PacienteEntity> pacientes = pacienteService.listarPacientes(nome, telefone, email, pageable);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um paciente existente", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteEntity.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")})
    public ResponseEntity<PacienteEntity> atualizarPaciente(@PathVariable Long id, @RequestBody PacienteEntity pacienteAtualizado) {
        PacienteEntity paciente = pacienteService.atualizarPaciente(id, pacienteAtualizado);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um paciente", responses = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")})
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um paciente por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteEntity.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")})
    public ResponseEntity<PacienteEntity> buscarPacientePorId(@PathVariable Long id) {
        Optional<PacienteEntity> paciente = pacienteService.buscarPacientePorId(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}