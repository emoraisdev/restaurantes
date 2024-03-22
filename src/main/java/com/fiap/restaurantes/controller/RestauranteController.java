package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.entity.Restaurante;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurante> registrar(@RequestBody Restaurante restaurante) {
        var restauranteRegistrado = service.salvar(restaurante);
        return new ResponseEntity<>( restauranteRegistrado, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {

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
    public ResponseEntity<Object> alterar(@RequestBody Restaurante restaurante) {

        try {
            var restauranteAlterado = service.alterar(restaurante);
            return new ResponseEntity<>(restauranteAlterado, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> remover(@PathVariable Long id) {

        try {
            service.remover(id);
            return new ResponseEntity<>("Restaurante Removido", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<Restaurante>> listar(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {

        var restaurantes = service.listar(PageRequest.of(page, size));

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }
}
