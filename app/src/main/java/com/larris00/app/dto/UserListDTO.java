package com.larris00.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private Long id;
    private String nombre;
    private String email;
    private Boolean estado;
}
