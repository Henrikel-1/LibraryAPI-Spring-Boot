package com.henrikel.libraryapi.core.config;

import com.henrikel.libraryapi.model.Papel;
import com.henrikel.libraryapi.model.Usuario;
import com.henrikel.libraryapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.nome}")
    private String nomeAdmin;

    @Value("${admin.default.email}")
    private String emailAdmin;

    @Value("${admin.default.senha}")
    private String senhaAdmin;

    public AdminSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByPapel(Papel.ADMIN)) {
            Usuario admin = new Usuario(nomeAdmin, emailAdmin, passwordEncoder.encode(senhaAdmin), Papel.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("Admin padrão criado: " + emailAdmin);
        }
    }
}
