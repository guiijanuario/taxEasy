package br.com.zup.catalisa.APITaxEasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseItemPedidoDto {
    private Long id;
    private ResponseProdutoDto produto;
    private ResponsePedidoDto pedido;
    private BigDecimal precoUnitario;
    private int quantidade;
}
