package org.example.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Direccion {
    private String ciudad;
    private String calle;
    private String codigo_postal;
}
