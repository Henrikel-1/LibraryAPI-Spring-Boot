package com.henrikel.libraryapi.controller;

import com.henrikel.libraryapi.dto.UsuarioPatchDto;
import com.henrikel.libraryapi.dto.UsuarioRequestDTO;
import com.henrikel.libraryapi.dto.UsuarioResponseDTO;
import com.henrikel.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public Page<UsuarioResponseDTO> listarUsuarios(@PageableDefault(size = 10, sort = "nome") Pageable pageable){
        return usuarioService.listarUsuarios(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO salvarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario){
        return usuarioService.salvar(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/nome")
    public List<UsuarioResponseDTO> buscarPorNome(@RequestParam String nome){
        return usuarioService.buscarUsuarioQuery(nome);
    }
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id){
        return usuarioService.buscarUsuario(id);
    }
    @PutMapping("/{id}")
    public UsuarioResponseDTO alterarLivro(@RequestBody @Valid UsuarioRequestDTO dto, @PathVariable Long id){
        return usuarioService.alterarUsuario(id, dto);
    }
    @PatchMapping("/{id}")
    public UsuarioResponseDTO alterarAtributo(@RequestBody UsuarioPatchDto dto, @PathVariable Long id){
        return usuarioService.alterarAtributo(id, dto);
    }


}
