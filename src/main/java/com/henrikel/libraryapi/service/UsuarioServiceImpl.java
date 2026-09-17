package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.core.exception.TipoErro;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioPatchDto;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioRequestDTO;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioResponseDTO;
import com.henrikel.libraryapi.model.Papel;
import com.henrikel.libraryapi.model.Usuario;
import com.henrikel.libraryapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.henrikel.libraryapi.core.exception.TipoErro.CONFLITO;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public UsuarioResponseDTO salvarAdm(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.existsByEmailIgnoreCase(usuarioRequestDTO.email())){
            throw new BusinessException(CONFLITO, "Já existe um usuário com este email");
        }
        Usuario usuario = new Usuario(usuarioRequestDTO.nome(), usuarioRequestDTO.email(), usuarioRequestDTO.senha(), Papel.ADMIN );
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new BusinessException(TipoErro.RECURSO_NAO_ENCONTRADO, "Usuario não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioResponseDTO buscarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new BusinessException(TipoErro.RECURSO_NAO_ENCONTRADO, "Usuario não encontrado!"));
        return toResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO alterarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new BusinessException(TipoErro.RECURSO_NAO_ENCONTRADO, "Usuario não encontrado!"));
        if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dto.email(), id)){
            throw new BusinessException(CONFLITO, "Já existe um usuário com este email");
        }
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResponseDTO> buscarUsuarioQuery(String nome) {
        return usuarioRepository.findByNome(nome).stream().map(this::toResponse).toList();
    }
    @Override
    public UsuarioResponseDTO alterarAtributo(Long id, UsuarioPatchDto usuarioPatchDto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new BusinessException(TipoErro.RECURSO_NAO_ENCONTRADO, "Usuario não encontrado!"));
        if (usuarioPatchDto.nome() != null){
            usuario.setNome(usuarioPatchDto.nome());
        }
        if (usuarioPatchDto.email() != null){
            if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuarioPatchDto.email(), id)){
                throw new BusinessException(CONFLITO, "Já existe um usuário com este email");
            }
            usuario.setEmail(usuarioPatchDto.email());
        }
        if (usuarioPatchDto.senha() != null){
            usuario.setSenha(passwordEncoder.encode(usuarioPatchDto.senha()));
        }
        if (usuarioPatchDto.papel() != null){
            usuario.setPapel(usuarioPatchDto.papel());
        }
        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.existsByEmailIgnoreCase(usuarioRequestDTO.email())){
            throw new BusinessException(CONFLITO, "Já existe um usuário com este email");
        }
        Usuario usuario = new Usuario(usuarioRequestDTO.nome(), usuarioRequestDTO.email(), usuarioRequestDTO.senha(), Papel.USER );
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO toResponse(Usuario usuario){
        return new UsuarioResponseDTO(usuario.getNome(), usuario.getEmail(), usuario.getPapel());
    }

}
