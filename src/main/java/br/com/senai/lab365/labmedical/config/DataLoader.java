package br.com.senai.lab365.labmedical.config;

import br.com.senai.lab365.labmedical.entities.Perfil;
import br.com.senai.lab365.labmedical.entities.UsuarioEntity;
import br.com.senai.lab365.labmedical.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.findByEmail("admin@example.com").isPresent()) {
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNome("Admin");
            admin.setEmail("admin@example.com");
            admin.setDataNascimento(LocalDate.of(1970, 1, 1));
            admin.setCpf("00000000000");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setPerfil(Perfil.ADMIN);

            usuarioRepository.save(admin);
        }
    }
}