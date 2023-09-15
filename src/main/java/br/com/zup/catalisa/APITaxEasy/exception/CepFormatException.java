package br.com.zup.catalisa.APITaxEasy.exception;

public class CepFormatException extends RuntimeException {
    public CepFormatException(String mensagem) {
        super(mensagem);
    }
}
