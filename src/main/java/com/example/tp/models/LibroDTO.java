package com.example.tp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@NotNull
public class LibroDTO {
    @Id
    private int id;
    @NotNull
    private String titulo;
    @NotNull
    private String autor;
}