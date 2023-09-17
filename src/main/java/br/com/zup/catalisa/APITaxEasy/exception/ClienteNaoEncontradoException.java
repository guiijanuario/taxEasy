package br.com.zup.catalisa.APITaxEasy.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(Long clienteId) {
        super("Cliente não encontrado com o ID: " + clienteId);
    }
}
