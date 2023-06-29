package com.example.tp.controllers;

import com.example.tp.exceptions.*;
import com.example.tp.models.Estante;
import com.example.tp.services.EstanteService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/estante")
public class EstanteController {

    @Autowired
    private EstanteService es;

    private record EstanteResponse(Estante estante, String message) {
    }

    private record EstanteListResponse(List<Estante> estantes, String message) {
    }

    private List<String> validateEstante(Estante e) {
        List<String> errors = new ArrayList<>();
        if (e.getCapacidad() == null || e.getCapacidad().isEmpty()) {
            errors.add("La capacidad del estante es requerida");
        }
        return errors;
    }

    @PostMapping("/addEstante")
    public ResponseEntity<Object> addEstante(@RequestBody final @NotNull Estante e) {
        List<String> errors = validateEstante(e);
        try {
            if (!errors.isEmpty()) {
                String errorMessage = String.join("\n", errors);
                throw new BadRequestException(errorMessage);
            }
            return ResponseEntity.status(OK).body(new EstanteResponse(es.addEstante(e), "Estante cargado con éxito"));
        } catch (BadRequestException ex) {
            return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(BAD_REQUEST).body("Hubo un error al cargar el estante");
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        try {
            return ResponseEntity.status(OK).body(es.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Hubo un error al recuperar los estantes");
        }
    }

    @GetMapping("/total")
    public Integer getTotal() {
        return es.getTotal();
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Object> updateEstante(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Estante e) {
        try {
            Estante updatedEstante = es.updateEstante(id, e);
            if (updatedEstante == null) {
                return ResponseEntity.status(NOT_FOUND).body("Estante " + id + " no encontrado");
            }
            return ResponseEntity.status(OK).body(new EstanteResponse(updatedEstante, "Estante " + id + " actualizado con éxito"));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteEstante(@PathVariable final @NotNull Integer id) {
        return es.deleteEstante(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEstante(@PathVariable final @NotNull Integer id) {
        try {
            Estante estante = es.getEstante(id);
            if (estante == null) {
                return ResponseEntity.status(NOT_FOUND).body("Estante " + id + " no encontrado");
            }
            return ResponseEntity.status(OK).body(new EstanteResponse(es.getEstante(id), "Estante " + id + " recuperado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
