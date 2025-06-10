package com.larris00.app.service;

import com.larris00.app.model.Rol;

import java.util.Set;

public interface RolService {

    Set<Rol> obtenerRolesPorIds(Set<Long> ids);
}
