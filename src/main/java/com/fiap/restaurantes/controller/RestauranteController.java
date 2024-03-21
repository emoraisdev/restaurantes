package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.entity.Restaurante;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurante> registrar(@RequestBody Restaurante restaurante) {
        var restauranteRegistrado = service.salvar(restaurante);
        return new ResponseEntity<>( restauranteRegistrado, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        try {
            var mensagem = service.buscar(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> alterar(@RequestBody Restaurante restaurante) {

        try {
            var restauranteAlterado = service.alterar(restaurante);
            return new ResponseEntity<>(restauranteAlterado, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
