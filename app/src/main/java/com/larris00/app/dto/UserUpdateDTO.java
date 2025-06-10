package com.larris00.app.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private Boolean estado;

    @NotNull(message = "Debe proporcionar al menos un rol")
    private Set<@NotNull(message = "El ID del rol no puede ser nulo") Long> rolesIds;
}
