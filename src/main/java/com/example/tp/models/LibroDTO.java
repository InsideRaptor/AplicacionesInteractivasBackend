package com.example.tp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LibroDTO {
    private int id;
    private String titulo;
    private String autor;
    private List<LibroDTOMinimal> libros;

    @Data
    @AllArgsConstructor
    public static class LibroDTOMinimal {
        private int id;
        private String titulo;
        private String autor;
    }
}