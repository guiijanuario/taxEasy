package br.com.zup.catalisa.APITaxEasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClienteDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
}
