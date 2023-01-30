package org.example.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Direccion {
    private ObjectId id;
    private String calle;
    private int numero;
    private String poblacion;
    private String provincia;
}
