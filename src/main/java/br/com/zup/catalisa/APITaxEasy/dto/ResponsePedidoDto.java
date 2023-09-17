package br.com.zup.catalisa.APITaxEasy.dto;

import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import br.com.zup.catalisa.APITaxEasy.model.ItemPedidoModel;
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
    private ClienteModel cliente;
    private List<ItemPedidoModel> itens;
    private BigDecimal valorTotal;

}
