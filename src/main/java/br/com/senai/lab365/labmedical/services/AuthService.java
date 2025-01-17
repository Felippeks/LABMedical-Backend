package br.com.senai.lab365.labmedical.services;

import br.com.senai.lab365.labmedical.dtos.login.LoginRequestDTO;
import br.com.senai.lab365.labmedical.entities.UsuarioEntity;
import br.com.senai.lab365.labmedical.repositories.UsuarioRepository;
import br.com.senai.lab365.labmedical.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String autenticarUsuario(LoginRequestDTO loginRequestDTO) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuario.getPassword())) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }

        return jwtUtil.generateToken(usuario);
    }

    public Long getPacienteAutenticadoId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UsuarioEntity) {
            UsuarioEntity usuario = (UsuarioEntity) principal;
            if (usuario.getPaciente() == null) {
                throw new RuntimeException("Paciente não associado ao usuário autenticado");
            }
            return usuario.getPaciente().getId();
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

}
