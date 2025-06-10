package com.larris00.app.controller;

import com.larris00.app.dto.RoleDTO;
import com.larris00.app.model.Rol;
import com.larris00.app.repository.RolRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> listarRoles() {
        List<RoleDTO> lista = rolRepository.findAll().stream()
                .map(rol -> mapper.map(rol, RoleDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
