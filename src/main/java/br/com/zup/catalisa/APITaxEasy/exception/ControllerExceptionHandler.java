package br.com.zup.catalisa.APITaxEasy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ProdutoNaoEncontradoException.class, ClienteNaoEncontradoException.class})
    public ResponseEntity<String> handleNotFoundExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<List<String>> handleValidationException(ValidationErrorException ex) {
        List<String> errorMessages = ex.getErrorMessages();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

}
