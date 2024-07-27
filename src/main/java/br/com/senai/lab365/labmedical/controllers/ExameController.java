package br.com.senai.lab365.labmedical.controllers;

import br.com.senai.lab365.labmedical.dtos.exames.ExameRequestDTO;
import br.com.senai.lab365.labmedical.dtos.exames.ExameResponseDTO;
import br.com.senai.lab365.labmedical.exceptions.exames.ExameNaoEncontradoException;
import br.com.senai.lab365.labmedical.exceptions.responses.ApiResponseOK;
import br.com.senai.lab365.labmedical.services.ExameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameService exameService;

    @PostMapping
    @Operation(summary = "Cria um exame", description = "Cria um exame com os dados fornecidos", tags = {"exames"})
    @ApiResponse(responseCode = "201", description = "Exame criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<ApiResponseOK<ExameResponseDTO>> createExame(@RequestBody ExameRequestDTO exameRequestDTO) {
        ExameResponseDTO createdExame = exameService.createExame(exameRequestDTO);
        ApiResponseOK<ExameResponseDTO> response = new ApiResponseOK<>("Exame criado com sucesso", createdExame);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um exame", description = "Recupera um exame com base no ID fornecido", tags = {"exames"})
    @ApiResponse(responseCode = "200", description = "Exame recuperado com sucesso")
    @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<ApiResponseOK<ExameResponseDTO>> getExameById(@PathVariable Long id) {
        try {
            ExameResponseDTO exameResponseDTO = exameService.getExameById(id);
            ApiResponseOK<ExameResponseDTO> response = new ApiResponseOK<>("Exame recuperado com sucesso", exameResponseDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ExameNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um exame", description = "Atualiza um exame com base no ID fornecido", tags = {"exames"})
    @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<ApiResponseOK<ExameResponseDTO>> updateExame(@PathVariable Long id, @RequestBody ExameRequestDTO exameRequestDTO) {
        try {
            ExameResponseDTO updatedExame = exameService.updateExame(id, exameRequestDTO);
            ApiResponseOK<ExameResponseDTO> response = new ApiResponseOK<>("Exame atualizado com sucesso", updatedExame);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExameNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um exame", description = "Deleta um exame com base no ID fornecido", tags = {"exames"})
    @ApiResponse(responseCode = "200", description = "Exame deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    public ResponseEntity<ApiResponseOK<String>> deleteExame(@PathVariable Long id) {
        try {
            exameService.deleteExame(id);
            ApiResponseOK<String> response = new ApiResponseOK<>("Exame deletado com sucesso", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExameNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os exames", description = "Recupera a lista de todos os exames", tags = {"exames"})
    @ApiResponse(responseCode = "200", description = "Lista de exames recuperada com sucesso")
    public ResponseEntity<ApiResponseOK<Page<ExameResponseDTO>>> getAllExames(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExameResponseDTO> exames = exameService.getAllExames(pageable);
        ApiResponseOK<Page<ExameResponseDTO>> response = new ApiResponseOK<>("Lista de exames recuperada com sucesso", exames);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}