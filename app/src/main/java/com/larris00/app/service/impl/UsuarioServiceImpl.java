package com.larris00.app.service.impl;

import com.larris00.app.dto.*;
import com.larris00.app.model.Rol;
import com.larris00.app.model.Usuario;
import com.larris00.app.repository.RolRepository;
import com.larris00.app.repository.UsuarioRepository;
import com.larris00.app.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserDTO crearUsuario(UserCreateDTO dto) {
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new ValidationException("El email ya está registrado.");
        }

        Set<Rol> roles = obtenerRolesPorIds(dto.getRolesIds());

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .estado(true)
                .roles(roles)
                .build();

        usuario = usuarioRepo.save(usuario);
        return convertirADTO(usuario);
    }

    @Override
    @Transactional
    public UserDTO actualizarUsuario(Long id, UserUpdateDTO dto) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setPassword(dto.getPassword());
        usuario.setEstado(dto.getEstado());
        usuario.setRoles(obtenerRolesPorIds(dto.getRolesIds()));

        usuario = usuarioRepo.save(usuario);
        return convertirADTO(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        usuarioRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserListDTO> listarUsuarios() {
        return usuarioRepo.findAll().stream()
                .map(u -> mapper.map(u, UserListDTO.class))
                .collect(Collectors.toList());
    }

    private Set<Rol> obtenerRolesPorIds(Set<Long> ids) {
        return ids.stream().map(id ->
            rolRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Rol no válido: " + id))
        ).collect(Collectors.toSet());
    }

    private UserDTO convertirADTO(Usuario usuario) {
        UserDTO dto = mapper.map(usuario, UserDTO.class);
        dto.setRoles(usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()));
        return dto;
    }
}
