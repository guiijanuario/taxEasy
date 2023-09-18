package br.com.zup.catalisa.APITaxEasy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePedidoDto {
    private Long id;
    private List<ResponseProdutoDto> produtos;

    @JsonProperty("cep_entrega")
    private EnderecoResponseDto cepEntrega;

    @JsonProperty("numero_casa")
    private String numeroCasa;

    @JsonProperty("nome_cliente")
    private String nomeCliente;

    @JsonProperty("taxa_entrega")
    private BigDecimal taxaEntrega;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;
}
