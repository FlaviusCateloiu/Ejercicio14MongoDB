package org.example.entities;

import lombok.*;
@Data
@NoArgsConstructor
public class Personas {
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String telefono;
    private Direccion direccion;
}
