package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.UsuarioDto;
import br.com.zup.catalisa.APITaxEasy.model.UsuarioModel;
import br.com.zup.catalisa.APITaxEasy.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = " : Lista todos os usuários", method = "GET")
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
       List<UsuarioModel> usuarios = usuarioService.listarTodosUsuarios();
        List<UsuarioDto> usuariosResponseDto = usuarios.stream()
                .map(user -> new UsuarioDto(user.getUsername(), user.getPassword(), user.getRoles()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosResponseDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = " : Cadastra um novo usuário", method = "GET")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto usuarioDTO) {
        try {
            UsuarioModel usuarioCriado = usuarioService.criarNovoUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/usuarios/" + usuarioCriado.getId_usuario())
                    .build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(summary = " : Deleta um usuário", method = "GET")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long userId) {
        try {
            usuarioService.deletarUsuario(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
