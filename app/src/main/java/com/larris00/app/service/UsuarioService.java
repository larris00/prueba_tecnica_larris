package com.larris00.app.service;

import com.larris00.app.dto.*;

import java.util.List;

public interface UsuarioService {

    UserDTO crearUsuario(UserCreateDTO dto);

    UserDTO actualizarUsuario(Long id, UserUpdateDTO dto);

    void eliminarUsuario(Long id);

    UserDTO obtenerUsuario(Long id);

    List<UserListDTO> listarUsuarios();
}
