package br.com.zup.catalisa.APITaxEasy.dto;


import br.com.zup.catalisa.APITaxEasy.config.SistemaConfiguracaoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEmpresaDto {
    private String nomeEmpresa;
    private String cnpj;
    private String cepOrigem;
    private String numeroOrigem;
    private BigDecimal custoEntrega;

}
