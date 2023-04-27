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
@Table(name = "Libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    @NotNull
    @Column(name = "Titulo")
    private String titulo;
    @NotNull
    @Column(name = "Descripcion")
    private String descripcion;
    @NotNull
    @Column(name = "Autor")
    private String autor;
    @NotNull
    @Column(name = "Editorial")
    private String editorial;
    @NotNull
    @Column(name = "Estante")
    @ManyToOne
    @JoinColumn(name = "Libros")
    private Estante estante;
}
