package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.core.exception.TipoErro;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioRequestDTO;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioResponseDTO;
import com.henrikel.libraryapi.model.Papel;
import com.henrikel.libraryapi.model.Usuario;
import com.henrikel.libraryapi.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    @DisplayName("Deve salvar Adm caso não exista um usuario com o mesmo email")
    void deveSalvarAdmCasoNaoExistaUsuarioMesmoEmail() {
        //arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste", "teste@gmail.com", "teste123");
        when(usuarioRepository.existsByEmailIgnoreCase(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada-2123");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act:
        usuarioService.salvarAdm(dto);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(1)).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        //Assert:
        verify(passwordEncoder).encode(dto.senha());
        assertEquals(dto.nome(), usuarioSalvo.getNome());
        assertEquals(dto.email(), usuarioSalvo.getEmail());
        assertEquals("senha-criptografada-2123", usuarioSalvo.getSenha());
    }
    @Test
    @DisplayName("Deve lancar excecao quando existir adm com mesmo email")
    void deveLancarExcecaoCasoJaExistaAdmComMesmoEmail() {
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste", "teste@gmail.com", "teste123");
        when(usuarioRepository.existsByEmailIgnoreCase(dto.email())).thenReturn(true);
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.salvarAdm(dto));
        //Extra assert:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
    }

    @Test
    @DisplayName("Deve deletar usuario caso exista o Id")
    void deveDeletarUsuarioCasoExistaId() {
        //Arrange:
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        //Act:
        usuarioService.deletar(1L);
        //Assert:
        verify(usuarioRepository).delete(usuario);
    }
    @Test
    @DisplayName("Deve lancar excecao caso não exista o Id")
    void deveLancarExcecaoCasoNaoExistaId() {
        //Arrange:
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.deletar(1L));
        //Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve retornar o usuario caso exista o id")
    void deveRetornarUsuarioCasoExistaId() {
        //Arrange:
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        //Act:
        UsuarioResponseDTO resultado = usuarioService.buscarUsuario(1L);
        //Assert:
        assertEquals(usuario.getEmail(), resultado.email());
        assertEquals(usuario.getNome(), resultado.nome());
    }
    @Test
    @DisplayName("Deve lancar excecao caso o Id do usuario nao exista")
    void deveLancarExcecaoCasoIdNaoExistir() {
        //Arrange:
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.buscarUsuario(1L));
        //Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
}