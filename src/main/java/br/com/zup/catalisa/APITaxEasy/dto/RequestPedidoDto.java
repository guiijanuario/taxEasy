package br.com.zup.catalisa.APITaxEasy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RequestPedidoDto(

        @JsonProperty("produtos_ids")
        List<Long> produtosIds,

        @JsonProperty("cep_entrega")
        String cepEntrega,

        @JsonProperty("numero_casa_entrega")
        String numeroCasaEntrega,

        @JsonProperty("cliente_id")
        Long clienteId) {
}
