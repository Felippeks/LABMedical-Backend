package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.dtos.exames.ExameRequestDTO;
import br.com.senai.lab365.labmedical.dtos.exames.ExameResponseDTO;
import br.com.senai.lab365.labmedical.services.ExameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameService exameService;

    @PostMapping
    @Operation(summary = "Cria um exame", description = "Cria um exame com os dados fornecidos", tags = {"exames"})
        @ApiResponse(responseCode = "201", description = "Exame criado com sucesso")
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<ExameResponseDTO> createExame(@RequestBody ExameRequestDTO exameRequestDTO) {
        ExameResponseDTO createdExame = exameService.createExame(exameRequestDTO);
        return new ResponseEntity<>(createdExame, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um exame", description = "Recupera um exame com base no ID fornecido", tags = {"exames"})
        @ApiResponse(responseCode = "200", description = "Exame recuperado com sucesso")
        @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<ExameResponseDTO> getExameById(@PathVariable Long id) {
        ExameResponseDTO exameResponseDTO = exameService.getExameById(id);
        return new ResponseEntity<>(exameResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um exame", description = "Atualiza um exame com base no ID fornecido", tags = {"exames"})
        @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso")
        @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<ExameResponseDTO> updateExame(@PathVariable Long id, @RequestBody ExameRequestDTO exameRequestDTO) {
        ExameResponseDTO updatedExame = exameService.updateExame(id, exameRequestDTO);
        return new ResponseEntity<>(updatedExame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um exame", description = "Deleta um exame com base no ID fornecido", tags = {"exames"})
        @ApiResponse(responseCode = "204", description = "Exame deletado com sucesso")
        @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<Void> deleteExame(@PathVariable Long id) {
        exameService.deleteExame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Lista todos os exames", description = "Recupera a lista de todos os exames", tags = {"exames"})
    @ApiResponse(responseCode = "200", description = "Lista de exames recuperada com sucesso")
    public ResponseEntity<List<ExameResponseDTO>> getAllExames() {
        List<ExameResponseDTO> exames = exameService.getAllExames();
        return new ResponseEntity<>(exames, HttpStatus.OK);
    }
}