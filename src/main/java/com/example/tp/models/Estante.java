package com.example.tp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Estante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Estante_id")
    private int id;
    @NotNull
    private String capacidad;
    @NotNull
    @Column(name = "Libros")
    @OneToMany(mappedBy = "estante")
    @JsonIgnore
    private List<Libro> listaLibros;
}
