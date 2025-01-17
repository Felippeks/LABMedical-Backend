package br.com.senai.lab365.labmedical.exceptions;

import br.com.senai.lab365.labmedical.exceptions.consulta.ConsultaNaoEncontradaException;
import br.com.senai.lab365.labmedical.exceptions.exames.BadRequestException;
import br.com.senai.lab365.labmedical.exceptions.exames.ExameNaoEncontradoException;
import br.com.senai.lab365.labmedical.exceptions.exames.ResourceNotFoundException;
import br.com.senai.lab365.labmedical.exceptions.paciente.CampoObrigatorioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.senai.lab365.labmedical.exceptions.paciente.CpfJaCadastradoException;
import br.com.senai.lab365.labmedical.exceptions.paciente.PacienteNaoEncontradoException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<String> handlePacienteNaoEncontrado(PacienteNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<?> handleCpfJaCadastrado(CpfJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CampoObrigatorioException.class)
    public ResponseEntity<?> handleCampoObrigatorioException(CampoObrigatorioException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Acesso Negado: Você não tem as permissões necessárias para acessar este recurso.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExameNaoEncontradoException.class)
    public ResponseEntity<String> handleExameNaoEncontradoException(ExameNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConsultaNaoEncontradaException.class)
    public ResponseEntity<String> handleConsultaNaoEncontradaException(ConsultaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}