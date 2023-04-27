package com.example.tp.services;

import com.example.tp.models.Libro;
import com.example.tp.models.LibroDTO;
import com.example.tp.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class LibroService {

    private final LibroRepository lr;

    @Autowired
    public LibroService(LibroRepository lr) {
        this.lr = lr;
    }

    public ResponseEntity addLibro(Libro l) {
        try {
            lr.save(l);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Libro> getAll() {
        return lr.findAll();
    }

    public Integer getTotal() {
        return lr.findAll().size();
    }

    public ResponseEntity updateLibro(Integer id, Libro libro) {
        try {
            Libro l = lr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Libro no encontrado"));
            l.setTitulo(l.getTitulo());
            l.setAutor(l.getAutor());
            l.setEditorial(l.getEditorial());
            lr.save(l);
            return ResponseEntity.ok(lr.save(l));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity deleteLibro(Integer id) {
        try {
            lr.deleteById(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public Libro getLibro(Integer id) {
        return lr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Libro" + getLibro(id).getTitulo() + "no encontrado"));
    }

    public LibroDTO getByAutor(String autor) {
        return (LibroDTO) Arrays.stream(lr.findAll().toArray()).filter(l -> ((Libro) l).getAutor().equals(autor)).findFirst().orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Libro no encontrado"));
    }
}
