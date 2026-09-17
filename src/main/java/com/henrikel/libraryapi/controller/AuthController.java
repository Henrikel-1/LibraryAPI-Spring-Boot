package com.henrikel.libraryapi.controller;

import com.henrikel.libraryapi.core.security.JwtService;
import com.henrikel.libraryapi.dto.usuarioDTOS.LoginRequestDto;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioRequestDTO;
import com.henrikel.libraryapi.dto.usuarioDTOS.UsuarioResponseDTO;
import com.henrikel.libraryapi.service.UsuarioService;
import com.henrikel.libraryapi.service.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticatorManager;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UsuarioResponseDTO> signup(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto login){
        var authToken = new UsernamePasswordAuthenticationToken(login.email(),login.senha());
        var  authentication = authenticatorManager.authenticate(authToken);

        UserDetails usuario = (UserDetails) authentication.getPrincipal();
        String token = jwtService.gerarToken(usuario);

        return ResponseEntity.ok(token);
    }
}
