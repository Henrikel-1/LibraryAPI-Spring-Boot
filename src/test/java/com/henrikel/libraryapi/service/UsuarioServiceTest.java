package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.core.exception.TipoErro;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioPatchDto;
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

import java.util.List;
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
        //Assert:
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();

        verify(passwordEncoder).encode(dto.senha());
        assertEquals(dto.nome(), usuarioSalvo.getNome());
        assertEquals(dto.email(), usuarioSalvo.getEmail());
        assertEquals("senha-criptografada-2123", usuarioSalvo.getSenha());
        assertEquals(Papel.ADMIN, usuarioSalvo.getPapel());
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
        verify(usuarioRepository, never()).save(any(Usuario.class));
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
    @Test
    @DisplayName("Deve salvar usuario caso não exista um usuario com mesmo email")
    void deveSalvarCasoJaNaoExistaEsteEmail(){
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste", "teste@gmail.com", "teste123");
        when(usuarioRepository.existsByEmailIgnoreCase(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada-2123");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act:
        usuarioService.salvarUsuario(dto);
        //Assert:
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        verify(passwordEncoder).encode(dto.senha());
        assertEquals(dto.nome(), usuarioSalvo.getNome());
        assertEquals(dto.email(), usuarioSalvo.getEmail());
        assertEquals("senha-criptografada-2123", usuarioSalvo.getSenha());
        assertEquals(Papel.USER, usuarioSalvo.getPapel());

    }
    @Test
    @DisplayName("Deve lancar excecao quando existir usuario com mesmo email")
    void deveLancarExcecaoCasoJaExistaUsuarioComMesmoEmail() {
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste", "teste@gmail.com", "teste123");
        when(usuarioRepository.existsByEmailIgnoreCase(dto.email())).thenReturn(true);
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.salvarUsuario(dto));
        //Extra assert:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    @Test
    @DisplayName("Deve retornar a lista dos usuarios com mesmo nome")
    void deveRetornarListaUsuariosMesmoNome(){
        //Arrange:
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findByNome("teste")).thenReturn(List.of(usuario));
        //Act:
        List<UsuarioResponseDTO> lista = usuarioService.buscarUsuarioQuery("teste");
        //Assert:
        assertEquals(1, lista.size());
        assertEquals(usuario.getNome(), lista.getFirst().nome());
        assertEquals(usuario.getEmail(), lista.getFirst().email());
        assertEquals(usuario.getPapel(), lista.getFirst().papel());
    }
    @Test
    @DisplayName("Deve alterar usuario caso id exista e não exista outro usuario com mesmo email")
    void deveAlterarUsuarioCasoIdExistaENaoExistaOutroUsuarioComMesmoEmail(){
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste2", "teste2@gmail.com", "teste1234");
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada-2123");
        when(usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dto.email(), 1L)).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act:
        usuarioService.alterarUsuario(1L, dto);

        //Assert:
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        verify(passwordEncoder).encode(dto.senha());
        assertEquals(dto.nome(), usuarioSalvo.getNome());
        assertEquals(dto.email(), usuarioSalvo.getEmail());
        assertEquals("senha-criptografada-2123", usuarioSalvo.getSenha());
        assertEquals(Papel.ADMIN, usuarioSalvo.getPapel());
    }
    @Test
    @DisplayName("Deve lancar excecao caso id nao exista")
    void deveLancarExcecaoCasoIdNaoExista(){
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste2", "teste2@gmail.com", "teste1234");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.alterarUsuario(1L, dto));
        //Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    @Test
    @DisplayName("Deve lancar excecao caso já exista um usuario com este email")
    void deveLancarExcecaoCasoExistaUsuarioComMesmoEmail(){
        //Arrange:
        UsuarioRequestDTO dto = new UsuarioRequestDTO("teste2", "teste2@gmail.com", "teste1234");
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dto.email(), 1L)).thenReturn(true);
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.alterarUsuario(1L, dto));
        //Extra assert:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    @Test
    @DisplayName("alterarAtributo: Deve alterar nome e senha quando email não for informado")
    void alterarAtributo_deveAlterarParcialmenteAtributosCasoIdExitirENaoExistirUsuarioComMesmoEmail(){
        //Arrange:
        UsuarioPatchDto dto = new UsuarioPatchDto("Teste", null, "teste123");
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada-2123");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act:
        usuarioService.alterarAtributo(1L, dto);
        //Assert:
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        verify(passwordEncoder).encode(dto.senha());
        assertEquals("teste@gmail", usuarioSalvo.getEmail());
        assertEquals(dto.nome(), usuarioSalvo.getNome());
        assertEquals("senha-criptografada-2123", usuarioSalvo.getSenha());
    }
    @Test
    @DisplayName("alterarAtributo: deve lançar exceção quando o id não existir")
    void alterarAtributo_deveLancarExcecaoQuandoIdNaoExistir(){
        //Arrange:
        UsuarioPatchDto dto = new UsuarioPatchDto("Teste", null, null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.alterarAtributo(1L, dto));
        //Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    @Test
    @DisplayName("alterarAtributo: deve lançar exceção quando já existir outro usuário com mesmo email")
    void alterarAtributo_deveLancarExcecaoQuandoEmailDuplicado(){
        //Arrange:
        UsuarioPatchDto dto = new UsuarioPatchDto(null, "novo@gmail.com", null);
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dto.email(), 1L)).thenReturn(true);
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> usuarioService.alterarAtributo(1L, dto));
        //Extra assert:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    @Test
    @DisplayName("alterarAtributo: deve alterar todos os atributos informados")
    void alterarAtributo_deveAlterarTodosOsAtributos(){
        //Arrange:
        UsuarioPatchDto dto = new UsuarioPatchDto("Novo Nome", "novo@gmail.com", "novaSenha123");
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dto.email(), 1L)).thenReturn(false);
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada-2123");
        //Act:
        UsuarioResponseDTO resultado = usuarioService.alterarAtributo(1L, dto);
        //Assert:
        assertEquals(dto.nome(), resultado.nome());
        assertEquals(dto.email(), resultado.email());
        assertEquals(Papel.ADMIN, resultado.papel());
        verify(usuarioRepository).save(usuario);
    }
    @Test
    @DisplayName("alterarAtributo: deve alterar parcialmente preservando os campos não informados")
    void alterarAtributo_deveAlterarParcialmente(){
        //Arrange:
        UsuarioPatchDto dto = new UsuarioPatchDto("Novo Nome", null, null);
        Usuario usuario = new Usuario(1L,"teste", "teste@gmail", "teste123", Papel.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        String emailOriginal = usuario.getEmail();
        String senhaOriginal = usuario.getSenha();
        //Act:
        UsuarioResponseDTO resultado = usuarioService.alterarAtributo(1L, dto);
        //Assert:
        assertEquals(dto.nome(), resultado.nome());
        assertEquals(emailOriginal, resultado.email());
        assertEquals(senhaOriginal, usuario.getSenha());
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).existsByEmailIgnoreCaseAndIdNot(any(), any());
    }


}