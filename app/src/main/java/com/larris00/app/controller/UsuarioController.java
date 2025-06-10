package com.larris00.app.controller;

import com.larris00.app.dto.*;
import com.larris00.app.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UserCreateDTO dto,
                                org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Errores de validación: " + errores);
        }

        try {
            UserDTO creado = usuarioService.crearUsuario(dto);
            return ResponseEntity
                    .created(URI.create("/api/usuarios/" + creado.getId()))
                    .body(creado);
        } catch (jakarta.validation.ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Ocurrió un error inesperado al crear el usuario.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        try {
            UserDTO actualizado = usuarioService.actualizarUsuario(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (jakarta.persistence.EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception ex) {
        return ResponseEntity.status(500).body("Error en el Servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception ex) {
        return ResponseEntity.status(500).body("Error en el Servidor");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            UserDTO usuario = usuarioService.obtenerUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (jakarta.persistence.EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception ex) {
        return ResponseEntity.status(500).body("Error en el Servidor");
        }
    }
    
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<UserListDTO> usuarios = usuarioService.listarUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(204).body("No hay usuarios registrados");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error en el Servidor");
            }
    }
}

