package com.example.tp.controllers;

import com.example.tp.models.Estante;
import com.example.tp.models.Libro;
import com.example.tp.services.EstanteService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estante")
public class EstanteController {

    @Autowired
    private EstanteService es;

    @PostMapping("")
    public ResponseEntity addEstante(@RequestBody final @NotNull Estante e) {
        return es.addEstante(e);
    }

    @GetMapping("/getAll")
    public List<Estante> getAll() {
        return es.getAll();
    }

    @GetMapping("/total")
    public Integer getTotal() {
        return es.getTotal();
    }

    @PostMapping("/{id}/update")
    public ResponseEntity updateEstante(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Estante e) {
        return es.updateEstante(id, e);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteEstante(@PathVariable final @NotNull Integer id) {
        return es.deleteEstante(id);
    }

    @GetMapping("/{id}")
    public Estante getEstante(@PathVariable final @NotNull Integer id) {
        return es.getEstante(id);
    }
}
