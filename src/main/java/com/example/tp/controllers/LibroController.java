package com.example.tp.controllers;

import com.example.tp.exceptions.*;
import com.example.tp.models.Libro;
import com.example.tp.models.LibroDTO;
import com.example.tp.services.LibroService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService ls;

    private record LibroResponse(Libro libro, String message) {
    }

    private record LibroListResponse(List<Libro> libros, String message) {
    }

    public record LibroListResponseDTO(List<LibroDTO.LibroDTOMinimal> libros) {
    }

    private List<String> validateLibro(Libro l) {
        List<String> errors = new ArrayList<>();
        if (l.getTitulo() == null || l.getTitulo().isEmpty()) {
            errors.add("El título del libro es requerido");
        }
        if (l.getDescripcion() == null || l.getDescripcion().isEmpty()) {
            errors.add("La descripción del libro es requerida");
        }
        if (l.getAutor() == null || l.getAutor().isEmpty()) {
            errors.add("El autor del libro es requerido");
        }
        if (l.getEditorial() == null || l.getEditorial().isEmpty()) {
            errors.add("La editorial del libro es requerida");
        }
        if (l.getEstante() == null) {
            errors.add("El estante del libro es requerido");
        }
        return errors;
    }

    @PostMapping("/addLibro")
    public ResponseEntity<Object> addLibro(@RequestBody final @NotNull Libro l) {
        List<String> errors = validateLibro(l);
        try {
            if (!errors.isEmpty()) {
                String errorMessage = String.join("\n", errors);
                throw new BadRequestException(errorMessage);
            }
            return ResponseEntity.status(OK).body(new LibroResponse(ls.addLibro(l), "Libro cargado con éxito"));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(BAD_REQUEST).body("Hubo un error al cargar el libro");
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        try {
            return ResponseEntity.status(OK).body(new LibroListResponse(ls.getAll(), "Libros recuperados con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Hubo un error al recuperar los libros");
        }
    }

    @GetMapping("/total")
    public Integer getTotal() {
        return ls.getTotal();
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Object> updateLibro(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Libro l) {
        try {
            Libro updatedLibro = ls.updateLibro(id, l);
            if (updatedLibro == null) {
                return ResponseEntity.status(NOT_FOUND).body("Libro " + id + " no encontrado");
            }
            return ResponseEntity.status(OK).body(new LibroResponse(updatedLibro, "Libro " + id + " actualizado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteLibro(@PathVariable final @NotNull Integer id) {
        return ls.deleteLibro(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getLibro(@PathVariable final @NotNull Integer id) {
        try {
            Libro libro = ls.getLibro(id);
            if (libro == null) {
                return ResponseEntity.status(NOT_FOUND).body("Libro " + id + " no encontrado");
            }
            return ResponseEntity.status(OK).body(new LibroResponse(ls.getLibro(id), "Libro " + id + " recuperado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<Object> getByAutor(@PathVariable final @NotNull String autor) {
        Optional<List<LibroDTO.LibroDTOMinimal>> optionalLibros = ls.getByAutor(autor);
        return optionalLibros.<ResponseEntity<Object>>map(libroDTOMinimals -> new ResponseEntity<>(new LibroListResponseDTO(libroDTOMinimals), OK))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).body("No se encontró ningún libro con el autor: " + autor));
    }
}
