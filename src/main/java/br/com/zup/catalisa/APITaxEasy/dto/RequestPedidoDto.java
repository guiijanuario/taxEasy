package br.com.zup.catalisa.APITaxEasy.dto;

import java.util.List;

public record RequestPedidoDto(
        List<Long> produtosIds,
        String cepEntrega,
        String numeroCasaEntrega,
        Long clienteId) {
}
