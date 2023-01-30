package org.example.entities;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Alumno {
    private ObjectId id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String nia;
    private String telefono;
    @ToString.Exclude
    private List<Modulo> modulos = new ArrayList<>();
}
