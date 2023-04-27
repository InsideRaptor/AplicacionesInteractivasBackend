package com.example.tp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Libro_id")
    private int id;
    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    @NotNull
    private String autor;
    @NotNull
    private String editorial;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Estante_id")
    private Estante estante;
}
