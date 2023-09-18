package br.com.zup.catalisa.APITaxEasy.dto;

import java.math.BigDecimal;

public record RequestEmpresaDto(
        String nomeEmpresa,
        String cnpj,
        String cepOrigem,
        String numeroOrigem,
        BigDecimal custoEntrega
) {
}
