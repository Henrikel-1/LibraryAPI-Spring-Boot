package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.dto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable);
    public UsuarioResponseDTO salvar(UsuarioRequestDTO usuarioRequestDTO);
    void deletar(Long id);
    UsuarioResponseDTO buscarUsuario(Long id);
    UsuarioResponseDTO alterarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO);
    List<UsuarioResponseDTO> buscarUsuarioQuery(String nome);
    UsuarioResponseDTO alterarAtributo(Long id, UsuarioPatchDto usuarioPatchDto);
}
