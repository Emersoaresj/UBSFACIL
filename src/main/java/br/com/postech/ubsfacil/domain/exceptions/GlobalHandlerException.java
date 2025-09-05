package br.com.postech.ubsfacil.domain.exceptions;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.api.dto.insumos.TipoInsumo;
import br.com.postech.ubsfacil.domain.exceptions.estoque.EstoqueNotFoundException;
import br.com.postech.ubsfacil.domain.exceptions.insumos.InsumoNotFoundException;
import br.com.postech.ubsfacil.domain.exceptions.ubs.UbsNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {


    // Constantes para as chaves de resposta
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MENSAGEM = "mensagem";
    private static final String PATH = "path";


    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroInternoException erroInternoException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "Erro interno da aplicação.");
        response.put(MENSAGEM, erroInternoException.getMessage());
        response.put(PATH, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErroNegocioException.class)
    public ResponseEntity<Map<String, Object>> handlerErroRegraNegocio(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        response.put(ERROR, "Erro de request.");
        response.put(MENSAGEM, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // PARA VALIDAÇÃO DE CAMPOS NA REQUISIÇÃO
    public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UbsNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerUbsNotFoundException(UbsNotFoundException ubsNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, ubsNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de requisição.");
        response.put("mensage", "Valor inválido para Tipo. Valores aceitos: " +
                Arrays.toString(TipoInsumo.values()));
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(TipoAlertaException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentTipoAlertaException(TipoAlertaException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put(MENSAGEM, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InsumoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerInsumoNotFoundException(InsumoNotFoundException insumoNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, insumoNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstoqueNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerEstoqueNotFoundException(EstoqueNotFoundException estoqueNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, estoqueNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
