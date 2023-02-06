package org.example.entities;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Modulo {
    private ObjectId id;
    @NonNull
    private String nombre;
    @NonNull
    private String curso;
    @NonNull
    private int horas;
    @ToString.Exclude
    private List<Alumno> alumnos = new ArrayList<>();
    @NonNull
    private Profesor profesor;

    public void addToListAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }

}
