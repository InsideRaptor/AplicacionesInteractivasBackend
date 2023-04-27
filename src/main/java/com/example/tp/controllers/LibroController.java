package com.example.tp.controllers;

import com.example.tp.models.Libro;
import com.example.tp.services.LibroService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService ls;

    @PostMapping("")
    public ResponseEntity addLibro(@RequestBody final @NotNull Libro l) {
        return ls.addLibro(l);
    }

    @GetMapping("/getAll")
    public List<Libro> getAll() {
        return ls.getAll();
    }

    @GetMapping("/total")
    public Integer getTotal() {
        return ls.getTotal();
    }

    @PostMapping("/{id}/update")
    public ResponseEntity updateZapatilla(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Libro l) {
        return ls.updateLibro(id, l);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteLibro(@PathVariable final @NotNull Integer id) {
        return ls.deleteLibro(id);
    }

    @GetMapping("/{id}")
    public Libro getLibro(@PathVariable final @NotNull Integer id) {
        return ls.getLibro(id);
    }

    @GetMapping("/dni/{dni}")
    public Libro getByAutor(@PathVariable final @NotNull String autor) {
        return ls.getByAutor(autor);
    }
}
