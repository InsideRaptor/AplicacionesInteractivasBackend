package com.example.tp.services;

import com.example.tp.models.Estante;
import com.example.tp.repositories.EstanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class EstanteService {

    private final EstanteRepository er;

    @Autowired
    public EstanteService(EstanteRepository er) {
        this.er = er;
    }

    public ResponseEntity addEstante(Estante est) {
        try {
            er.save(est);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Estante> getAll() {
        return er.findAll();
    }

    public Integer getTotal() {
        return er.findAll().size();
    }

    public ResponseEntity updateEstante(Integer id, Estante estante) {
        try {
            Estante est = er.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Estante no encontrado"));
            est.setCapacidad(est.getCapacidad());
            est.setListaLibros(est.getListaLibros());
            return ResponseEntity.ok(er.save(est));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity deleteEstante(Integer id) {
        try {
            er.deleteById(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public Estante getEstante(Integer id) {
        return er.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Estante no encontrado"));
    }
}