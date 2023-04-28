package com.example.tp.services;

import com.example.tp.exceptions.*;
import com.example.tp.models.Libro;
import com.example.tp.models.LibroDTO;
import com.example.tp.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LibroService {

    private final LibroRepository lr;

    @Autowired
    public LibroService(LibroRepository lr) {
        this.lr = lr;
    }

    public Libro addLibro(Libro l) {
        return lr.save(l);
    }

    public List<Libro> getAll() {
        return lr.findAll();
    }

    public Integer getTotal() {
        try {
            return lr.findAll().size();
        } catch (Exception e) {
            throw new InternalServerException("Hubo un error al recuperar el total de libros");
        }
    }

    public Libro updateLibro(Integer id, Libro libro) {
        Libro l = lr.findById(id).orElse(null);
        if (l != null) {
            l.setTitulo(libro.getTitulo());
            l.setDescripcion(libro.getDescripcion());
            l.setAutor(libro.getAutor());
            l.setEditorial(libro.getEditorial());
            l.setEstante(libro.getEstante());
            lr.save(l);
        }
        return l;
    }

    public ResponseEntity<String> deleteLibro(Integer id) {
        if (lr.existsById(id)) {
            try {
                lr.deleteById(id);
                return ResponseEntity.status(OK).body("Libro " + id + " eliminado con éxito");
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
            }
        }
        return ResponseEntity.status(NOT_FOUND).body("Libro " + id + " no encontrado");
    }

    public Libro getLibro(Integer id) {
        return lr.findById(id).orElse(null);
    }

    private static class LibroMapper {
        public static LibroDTO.LibroDTOMinimal toLibroDTOMinimal(Libro libro) {
            return new LibroDTO.LibroDTOMinimal(
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor()
            );
        }
    }

    public Optional<List<LibroDTO.LibroDTOMinimal>> getByAutor(String autor) {
        List<Libro> libros = lr.findByAutor(autor);
        if (libros.isEmpty()) {
            return Optional.empty();
        }
        List<LibroDTO.LibroDTOMinimal> libroDTOMinimals = libros.stream()
                .map(LibroMapper::toLibroDTOMinimal)
                .collect(Collectors.toList());
        return Optional.of(libroDTOMinimals);
    }
}
