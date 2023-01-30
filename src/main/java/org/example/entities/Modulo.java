package org.example.entities;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Modulo {
    private ObjectId id;
    private String nombre;
    private String curso;
    private int horas;
    private List<Alumno> alumnos = new ArrayList<>();
}
