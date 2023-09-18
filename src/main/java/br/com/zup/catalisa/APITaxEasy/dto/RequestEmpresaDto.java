package br.com.zup.catalisa.APITaxEasy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record RequestEmpresaDto(
        @JsonProperty("nome_empresa")
        String nomeEmpresa,
        String cnpj,
        @JsonProperty("cep_origem")
        String cepOrigem,

        @JsonProperty("numero_origem")
        String numeroOrigem,

        @JsonProperty("custo_entrega")
        BigDecimal custoEntrega
) {
}
