package br.com.zup.catalisa.APITaxEasy.exception;

public class EmpresaNaoEncontradaException extends RuntimeException{

    public EmpresaNaoEncontradaException(Long empresaId) {
        super("Empresa não encontrado com o ID: " + empresaId);
    }
}
