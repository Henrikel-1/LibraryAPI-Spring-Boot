package com.henrikel.libraryapi.repository;

import com.henrikel.libraryapi.model.Papel;
import com.henrikel.libraryapi.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar True quando existir outro usuario com mesmo email")
    void existsByEmailIgnoreCase_deveRetornarTrueQuandoExistirOutroUsuarioMesmoEmail(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        usuarioRepository.save(usuario);
        // Act:
        boolean teste = usuarioRepository.existsByEmailIgnoreCase("email@gmail.com");
        // Assert:
        assertTrue(teste);
    }

    @Test
    @DisplayName("Deve retornar False quando nao existir outro usuario com mesmo email")
    void existsByEmailIgnoreCase_deveRetornarFalseQuandoNaoExistirOutroUsuarioMesmoEmail(){
        // Arrange:
        // Act:
        boolean teste = usuarioRepository.existsByEmailIgnoreCase("email@gmail.com");
        // Assert:
        assertFalse(teste);
    }

    @Test
    @DisplayName("Deve retornar True quando existir outro usuario com mesmo email com id diferente")
    void existsByEmailIgnoreCaseAndIdNot_deveRetornarTrueQuandoExistirOutroUsuarioMesmoEmailComIdDiferente(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        Usuario salvo = usuarioRepository.save(usuario);
        Usuario usuario2 = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        usuarioRepository.save(usuario2);
        // Act:
        boolean teste = usuarioRepository.existsByEmailIgnoreCaseAndIdNot("email@gmail.com", salvo.getId());
        // Assert:
        assertTrue(teste);
    }
    @Test
    @DisplayName("Deve retornar False quando não existir outro usuario com mesmo email com id diferente")
    void existsByEmailIgnoreCaseAndIdNot_deveRetornarFalseQuandoNaoExistirOutroUsuarioMesmoEmailComIdDiferente(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        Usuario salvo = usuarioRepository.save(usuario);
        // Act:
        boolean teste = usuarioRepository.existsByEmailIgnoreCaseAndIdNot("email@gmail.com", salvo.getId());
        // Assert:
        assertFalse(teste);
    }
    @Test
    @DisplayName("Deve retornar uma lista de usuarios com mesmo nome")
    void findByNome_deveRetornarUmaListaDeUsuariosComMesmoNome(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        Usuario salvo = usuarioRepository.save(usuario);
        // Act:
        List<Usuario> lista = usuarioRepository.findByNome("nome");
        // Assert:
        assertEquals(1, lista.size());
        assertEquals(lista.getFirst().getId(), salvo.getId());
        assertEquals(lista.getFirst().getNome(), salvo.getNome());
        assertEquals(lista.getFirst().getEmail(), salvo.getEmail());
        assertEquals(lista.getFirst().getSenha(), salvo.getSenha());
        assertEquals(lista.getFirst().getPapel(), salvo.getPapel());
    }
    @Test
    @DisplayName("Deve retornar uma lista vazia caso não existir")
    void findByNome_deveRetornarUmaListaVaziaCasoNaoExistir(){
        // Arrange:
        // Act:
        List<Usuario> lista = usuarioRepository.findByNome("nome");
        // Assert:
        assertTrue(lista.isEmpty());
    }
    @Test
    @DisplayName("Deve retornar usuario caso email existir")
    void findByEmail_deveRetornarUsuarioCasoEmailExistir(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        usuarioRepository.save(usuario);
        // Act:
        Optional<Usuario> resultado = usuarioRepository.findByEmail("email@gmail.com");
        // Assert:
        assertTrue(resultado.isPresent());
        Usuario usuarioEncontrado = resultado.get();
        assertEquals("nome", usuarioEncontrado.getNome());
        assertEquals("email@gmail.com", usuarioEncontrado.getEmail());
    }
    @Test
    @DisplayName("Deve retornar vazio caso email não existir")
    void findByEmail_deveRetornarVazioCasoEmailNaoExistir(){
        // Arrange:
        // Act:
        Optional<Usuario> resultado = usuarioRepository.findByEmail("email@gmail.com");
        // Assert:
        assertTrue(resultado.isEmpty());
    }
    @Test
    @DisplayName("Deve retornar true caso papel existir")
    void existsByPapel_deveRetornarTrueCasoPapelExistir(){
        // Arrange:
        Usuario usuario = new Usuario("nome", "email@gmail.com", "senha123", Papel.ADMIN);
        usuarioRepository.save(usuario);
        // Act:
        boolean teste = usuarioRepository.existsByPapel(Papel.ADMIN);
        // Assert:
        assertTrue(teste);
    }
    @Test
    @DisplayName("Deve retornar false caso não papel existir")
    void existsByPapel_deveRetornarFalseCasoPapelNaoExistir(){
        // Arrange:
        // Act:
        boolean teste = usuarioRepository.existsByPapel(Papel.ADMIN);
        // Assert:
        assertFalse(teste);
    }

}
