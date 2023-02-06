package org.example.entities;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Alumno {
    private ObjectId id;
    @NonNull
    private String nombre;
    @NonNull
    private String primerApellido;
    @NonNull
    private String segundoApellido;
    @NonNull
    private String nia;
    @NonNull
    private String telefono;
    private List<Modulo> modulos = new ArrayList<>();

    public void addToListModulo(Modulo modulo) {
        modulos.add(modulo);
    }
}
