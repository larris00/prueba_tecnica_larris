package com.larris00.app.service.impl;

import com.larris00.app.model.Rol;
import com.larris00.app.repository.RolRepository;
import com.larris00.app.service.RolService;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepo;

    @Override
    public Set<Rol> obtenerRolesPorIds(Set<Long> ids) {
        return ids.stream()
                .map(id -> rolRepo.findById(id)
                        .orElseThrow(() -> new ValidationException("Rol no válido: " + id)))
                .collect(Collectors.toSet());
    }
}
