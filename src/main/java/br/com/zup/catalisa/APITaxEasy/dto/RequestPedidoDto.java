package br.com.zup.catalisa.APITaxEasy.dto;

import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import br.com.zup.catalisa.APITaxEasy.model.ItemPedidoModel;

import java.math.BigDecimal;
import java.util.List;

public record RequestPedidoDto(
        ClienteModel cliente,
        List<ItemPedidoModel> itens,
        BigDecimal valorTotal
) {
}
