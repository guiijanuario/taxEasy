package br.com.zup.catalisa.APITaxEasy.dto;

import br.com.zup.catalisa.APITaxEasy.model.PedidoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClienteDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private List<PedidoModel> pedidos;
}
