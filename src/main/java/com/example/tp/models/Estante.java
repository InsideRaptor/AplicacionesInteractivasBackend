package com.example.tp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "Estante")
public class Estante {
    @NotNull
    @Column(name = "Numero_estante")
    private String nroEstante;
    @NotNull
    @Column(name = "Capacidad")
    private String capacidad;
    @NotNull
    @Column(name = "Libros")
    @OneToMany(mappedBy = "estante")
    @JsonIgnore
    private List<Libro> listaLibros;
}
