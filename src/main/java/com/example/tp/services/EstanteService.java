package com.example.tp.services;

import com.example.tp.exceptions.InternalServerException;
import com.example.tp.models.Estante;
import com.example.tp.repositories.EstanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class EstanteService {

    private final EstanteRepository er;

    @Autowired
    public EstanteService(EstanteRepository er) {
        this.er = er;
    }

    public Estante addEstante(Estante est) {
        return er.save(est);
    }

    public List<Estante> getAll() {
        return er.findAll();
    }

    public Integer getTotal() {
        try {
            return er.findAll().size();
        } catch (Exception e) {
            throw new InternalServerException("Hubo un error al recuperar el total de estantes");
        }
    }

    public Estante updateEstante(Integer id, Estante estante) {
        Estante e = er.findById(id).orElse(null);
        if (e != null) {
            if (estante.getCapacidad() != null) {
                e.setCapacidad(estante.getCapacidad());
            }
            er.save(e);
        }
        return e;
    }

    public ResponseEntity<String> deleteEstante(Integer id) {
        if (er.existsById(id)) {
            try {
                er.deleteById(id);
                return ResponseEntity.status(OK).body("Estante " + id + " eliminado con éxito");
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Internal Server Error");
            }
        }
        return ResponseEntity.status(NOT_FOUND).body("Estante " + id + " no encontrado");
    }

    public Estante getEstante(Integer id) {
        return er.findById(id).orElse(null);
    }
}