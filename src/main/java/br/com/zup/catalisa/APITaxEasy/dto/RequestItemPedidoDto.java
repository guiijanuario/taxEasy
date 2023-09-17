package br.com.zup.catalisa.APITaxEasy.dto;

import java.math.BigDecimal;

public record RequestItemPedidoDto(
        RequestProdutoDto produto,
        RequestPedidoDto pedido,
        BigDecimal precoUnitario,
        int quantidade
) {
}
