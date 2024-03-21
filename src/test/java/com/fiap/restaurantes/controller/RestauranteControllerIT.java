package com.fiap.restaurantes.controller;


import com.fiap.restaurantes.entity.Restaurante;
import com.fiap.restaurantes.service.RestauranteService;
import com.fiap.restaurantes.utils.RestauranteHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.fiap.restaurantes.utils.RestauranteHelper.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RestauranteControllerIT {

    @Autowired
    RestauranteService service;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegistrarRestaurante {

        @Test
        void devePermitirRegistrarRestaurante (){

            var restaurante = gerarRestaurante();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(restaurante)
//                    .log().all()
                    .when()
                    .post("/restaurantes")
                    .then()
//                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath(
                            "schemas/restaurante.schema.json"));
        }

    }

    @Nested
    class BuscarRestaurante {

        @Test
        void devePermitirBuscarRestaurante() throws Exception {

            var id = 101L;
            when()
                    .get("/restaurantes/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecao_QuandoBuscarRestaurante_IdNaoExiste() throws Exception {

            var id = 1001L;
            when()
                    .get("/restaurantes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class AlterarRestaurante {

        @Test
        void devePermitirAlterarRestaurante() throws Exception {

            var id = 101L;
            var restaurante = gerarRestaurante();
            restaurante.setId(id);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(restaurante)
                    .when()
                    .put("/restaurantes")
                    .then()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .body(matchesJsonSchemaInClasspath(
                            "schemas/restaurante.schema.json"));
        }

        @Test
        void deveGerarExcecao_QuandoAlterarRestaurante_IdNaoExiste() throws Exception {

        }
    }
}
