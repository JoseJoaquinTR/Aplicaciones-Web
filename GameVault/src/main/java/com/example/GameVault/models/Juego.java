package com.example.GameVault.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, etc. (gracias a Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class Juego {

    private Long id;
    private String titulo;
    private String descripcion;

    // Almacenaremos el nombre del archivo
    private String portadaUrl;
}
