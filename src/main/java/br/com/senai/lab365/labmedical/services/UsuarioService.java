package br.com.senai.lab365.labmedical.services;

import br.com.senai.lab365.labmedical.dtos.cadastros.CadastroRequestDTO;
import br.com.senai.lab365.labmedical.dtos.cadastros.CadastroResponseDTO;
import br.com.senai.lab365.labmedical.entities.UsuarioEntity;
import br.com.senai.lab365.labmedical.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CadastroResponseDTO cadastrarUsuario(CadastroRequestDTO cadastroRequestDTO) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(cadastroRequestDTO.getNome());
        usuarioEntity.setEmail(cadastroRequestDTO.getEmail());
        usuarioEntity.setDataNascimento(cadastroRequestDTO.getDataNascimento());
        usuarioEntity.setCpf(cadastroRequestDTO.getCpf());
        usuarioEntity.setPassword(passwordEncoder.encode(cadastroRequestDTO.getPassword()));
        usuarioEntity.setPerfil(cadastroRequestDTO.getPerfil());

        usuarioEntity = usuarioRepository.save(usuarioEntity);

        CadastroResponseDTO cadastroResponseDTO = new CadastroResponseDTO();
        cadastroResponseDTO.setId(usuarioEntity.getId());
        cadastroResponseDTO.setNome(usuarioEntity.getNome());
        cadastroResponseDTO.setEmail(usuarioEntity.getEmail());
        cadastroResponseDTO.setDataNascimento(usuarioEntity.getDataNascimento());
        cadastroResponseDTO.setCpf(usuarioEntity.getCpf());
        cadastroResponseDTO.setPerfil(usuarioEntity.getPerfil());

        return cadastroResponseDTO;
    }
}
