package com.example.tp.services;

import com.example.tp.exceptions.*;
import com.example.tp.models.Libro;
import com.example.tp.models.LibroDTO;
import com.example.tp.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LibroService {

    private final LibroRepository lr;

    @Autowired
    public LibroService(LibroRepository lr) {
        this.lr = lr;
    }

    public void addLibro(Libro l) {
        lr.save(l);
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
            if (libro.getTitulo() != null) {
                l.setTitulo(libro.getTitulo());
            }
            if (libro.getDescripcion() != null) {
                l.setDescripcion(libro.getDescripcion());
            }
            if (libro.getAutor() != null) {
                l.setAutor(libro.getAutor());
            }
            if (libro.getEditorial() != null) {
                l.setEditorial(libro.getEditorial());
            }
            if (libro.getEstante() != null) {
                l.setEstante(libro.getEstante());
            }
            lr.save(l);
        }
        return l;
    }

    public ResponseEntity<Object> deleteLibro(Integer id) {
        if (lr.existsById(id)) {
            try {
                lr.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Libro eliminado con éxito");
                return ResponseEntity.status(OK).body(response);
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
            }
        }
        Map<String, Object> responseNotFound = new HashMap<>();
        responseNotFound.put("success", false);
        responseNotFound.put("message", "Libro no encontrado");
        return ResponseEntity.status(NOT_FOUND).body(responseNotFound);
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
