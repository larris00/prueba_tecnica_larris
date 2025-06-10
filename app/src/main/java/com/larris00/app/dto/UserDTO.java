package com.larris00.app.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nombre;
    private String email;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
    private Set<String> roles;
}

