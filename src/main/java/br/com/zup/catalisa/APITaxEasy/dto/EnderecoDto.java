package br.com.zup.catalisa.APITaxEasy.dto;

import lombok.Data;

@Data
public class EnderecoDto {
    private String origem;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
}
