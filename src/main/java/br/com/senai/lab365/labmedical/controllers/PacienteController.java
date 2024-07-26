package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.dtos.paciente.PacienteRequestDTO;
import br.com.senai.lab365.labmedical.dtos.paciente.PacienteResponseDTO;
import br.com.senai.lab365.labmedical.dtos.prontuarios.ProntuarioResponseDTO;
import br.com.senai.lab365.labmedical.exceptions.paciente.PacienteNaoEncontradoException;
import br.com.senai.lab365.labmedical.services.PacienteService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PacienteController(PacienteService pacienteService, ObjectMapper objectMapper) {
        this.pacienteService = pacienteService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @Operation(summary = "Cria um ou mais pacientes", responses = {
            @ApiResponse(responseCode = "201", description = "Paciente(s) criado(s) com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")})
    public ResponseEntity<?> criarPaciente(@RequestBody Object payload) {
        if (payload instanceof List) {
            List<PacienteRequestDTO> pacientes = objectMapper.convertValue(payload, new TypeReference<List<PacienteRequestDTO>>() {});
            List<PacienteResponseDTO> pacientesCriados = pacienteService.criarPacientesEmLote(pacientes);
            return ResponseEntity.status(201).body(pacientesCriados);
        } else {
            PacienteRequestDTO paciente = objectMapper.convertValue(payload, PacienteRequestDTO.class);
            PacienteResponseDTO pacienteCriado = pacienteService.criarPaciente(paciente);
            return ResponseEntity.status(201).body(pacienteCriado);
        }
    }

    @GetMapping
    @Operation(summary = "Lista pacientes com filtros opcionais", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))})
    public ResponseEntity<Page<PacienteResponseDTO>> listarPacientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        Page<PacienteResponseDTO> pacientes = pacienteService.listarPacientes(nome, telefone, email, pageable);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um paciente existente", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")})
    public ResponseEntity<PacienteResponseDTO> atualizarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDTO pacienteAtualizado) {
        PacienteResponseDTO paciente = pacienteService.atualizarPaciente(id, pacienteAtualizado);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um paciente", responses = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")})
    public ResponseEntity<String> excluirPaciente(@PathVariable Long id) {
        boolean excluido = pacienteService.excluirPaciente(id);
        if (excluido) {
            return ResponseEntity.ok("Paciente excluído com sucesso");
        } else {
            return ResponseEntity.status(404).body("Paciente não encontrado");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um paciente por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")})
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorId(@PathVariable Long id) {
        Optional<PacienteResponseDTO> paciente = pacienteService.buscarPacientePorId(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/prontuarios")
    @Operation(summary = "Lista pacientes para prontuário com filtros opcionais", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))})
    public ResponseEntity<Page<PacienteResponseDTO>> listarPacientesParaProntuario(
            @RequestParam(required = false) String nomeCompleto,
            @RequestParam(required = false) String numeroConvenio,
            Pageable pageable) {
        Page<PacienteResponseDTO> pacientes = pacienteService.listarPacientesParaProntuario(nomeCompleto, numeroConvenio, pageable);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}/prontuarios")
    @Operation(summary = "Lista prontuários de um paciente", responses = {
            @ApiResponse(responseCode = "200", description = "Prontuários do paciente recuperados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProntuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<ProntuarioResponseDTO> listarProntuariosDoPaciente(@PathVariable Long id) {
        try {
            ProntuarioResponseDTO prontuario = pacienteService.listarProntuariosDoPaciente(id);
            return ResponseEntity.ok(prontuario);
        } catch (PacienteNaoEncontradoException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Erro interno do servidor", ex);
        }
    }
}