package br.com.zup.catalisa.APITaxEasy.exception;

public class ItemPedidoNaoEncontradoException extends RuntimeException{
    public ItemPedidoNaoEncontradoException(Long itemPedidoId) {
        super("Item Pedido não encontrado com o ID: " + itemPedidoId);
    }
}
