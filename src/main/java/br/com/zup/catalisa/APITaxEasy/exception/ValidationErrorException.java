package br.com.zup.catalisa.APITaxEasy.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationErrorException extends RuntimeException{

    private final List<FieldError> fieldErrors;

    public ValidationErrorException(BindingResult bindingResult) {
        this.fieldErrors = bindingResult.getFieldErrors();
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public List<String> getErrorMessages() {
        return fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
