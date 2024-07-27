package br.com.senai.lab365.labmedical.controllers;


import br.com.senai.lab365.labmedical.dtos.cadastros.CadastroRequestDTO;
import br.com.senai.lab365.labmedical.dtos.cadastros.CadastroResponseDTO;
import br.com.senai.lab365.labmedical.dtos.login.LoginRequestDTO;
import br.com.senai.lab365.labmedical.dtos.login.LoginResponseDTO;
import br.com.senai.lab365.labmedical.services.UsuarioService;
import br.com.senai.lab365.labmedical.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthService authService;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na requisição")
    public ResponseEntity<CadastroResponseDTO> cadastrarUsuario(@Validated @RequestBody CadastroRequestDTO cadastroRequestDTO) {
        CadastroResponseDTO responseDTO = usuarioService.cadastrarUsuario(cadastroRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na requisição")
    public ResponseEntity<LoginResponseDTO> loginUsuario(@Validated @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.autenticarUsuario(loginRequestDTO);
        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
    }
}