package br.com.zup.catalisa.APITaxEasy.dto;

import java.math.BigDecimal;

public record RequestProdutoDto(
        String nome,
        String descricao,
        BigDecimal preco,
        String categoria
) {
}
