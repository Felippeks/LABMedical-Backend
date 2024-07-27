package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaRequestDTO;
import br.com.senai.lab365.labmedical.dtos.consultas.ConsultaResponseDTO;
import br.com.senai.lab365.labmedical.services.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Operation(summary = "Cria uma consulta", description = "Cria uma consulta com os dados fornecidos", tags = {"consultas"})
    @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ConsultaResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<ConsultaResponseDTO> createConsulta(@Valid @RequestBody ConsultaRequestDTO consultaRequestDTO) {
        ConsultaResponseDTO createdConsulta = consultaService.createConsulta(consultaRequestDTO);
        return new ResponseEntity<>(createdConsulta, HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera uma consulta", description = "Recupera uma consulta pelo ID", tags = {"consultas"})
    @ApiResponse(responseCode = "200", description = "Consulta recuperada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ConsultaResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    public ResponseEntity<ConsultaResponseDTO> getConsultaById(@PathVariable Long id) {
        try {
            Optional<ConsultaResponseDTO> consulta = consultaService.getConsultaById(id);
            return consulta.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma consulta", description = "Atualiza uma consulta pelo ID", tags = {"consultas"})
    @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ConsultaResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    public ResponseEntity<ConsultaResponseDTO> updateConsulta(@PathVariable Long id, @Validated @RequestBody ConsultaRequestDTO consultaRequestDTO) {
        try {
            ConsultaResponseDTO updatedConsulta = consultaService.updateConsulta(id, consultaRequestDTO);
            return new ResponseEntity<>(updatedConsulta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma consulta", description = "Deleta uma consulta pelo ID", tags = {"consultas"})
    @ApiResponse(responseCode = "204", description = "Consulta deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        try {
            consultaService.deleteConsulta(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as consultas", description = "Lista todas as consultas do banco de dados", tags = {"consultas"})
    @ApiResponse(responseCode = "200", description = "Consultas listadas com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ConsultaResponseDTO.class)))
    public ResponseEntity<List<ConsultaResponseDTO>> getAllConsultas() {
        List<ConsultaResponseDTO> consultas = consultaService.getAllConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/motivo/{motivoConsulta}")
    @Operation(summary = "Lista consultas por motivo", description = "Lista todas as consultas com o motivo fornecido", tags = {"consultas"})
    @ApiResponse(responseCode = "200", description = "Consultas listadas com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ConsultaResponseDTO.class)))
    public ResponseEntity<List<ConsultaResponseDTO>> getConsultasByMotivo(@PathVariable String motivoConsulta) {
        List<ConsultaResponseDTO> consultas = consultaService.getConsultasByMotivo(motivoConsulta);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }
}