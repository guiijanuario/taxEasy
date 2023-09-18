package br.com.zup.catalisa.APITaxEasy.exception;

public class ProdutosNaoEncontradosException extends RuntimeException{
    public ProdutosNaoEncontradosException() {
        super("Nenhum produto encontrado.");
    }
}
