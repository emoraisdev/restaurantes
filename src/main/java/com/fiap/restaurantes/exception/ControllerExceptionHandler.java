package com.fiap.restaurantes.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> EntityNotFoundException(EntityNotFoundException erro, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(getStandardError(HttpStatus.NOT_FOUND.value(), "Entidade Não Encontrada", erro.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> BusinessException(BusinessException erro, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(getStandardError(HttpStatus.NOT_FOUND.value(), "Erro na solicitação", erro.getMessage(), request.getRequestURI()));
    }

    private StandardError getStandardError(Integer status, String tipoErro, String mensagem, String uri){

        var erro = new StandardError();

        erro.setTimestamp(Instant.now());
        erro.setStatus(status);
        erro.setError(tipoErro);
        erro.setMessage(mensagem);
        erro.setPath(uri);

        return erro;
    }
}
