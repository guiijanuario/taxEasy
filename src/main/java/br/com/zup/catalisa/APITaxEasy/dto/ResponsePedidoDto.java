package br.com.zup.catalisa.APITaxEasy.dto;

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
    private EnderecoResponseDto cepEntrega;
    private String numeroCasa;
    private String nomeCliente;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
}
