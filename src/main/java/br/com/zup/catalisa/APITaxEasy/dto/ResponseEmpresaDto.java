package br.com.zup.catalisa.APITaxEasy.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEmpresaDto {

    @JsonProperty("nome_empresa")
    private String nomeEmpresa;
    private String cnpj;

    @JsonProperty("cep_origem")
    private String cepOrigem;

    @JsonProperty("numero_origem")
    private String numeroOrigem;

    @JsonProperty("custo_entrega")
    private BigDecimal custoEntrega;

}
