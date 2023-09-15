package br.com.zup.catalisa.APITaxEasy.dto;

public record RequestDestinoDto(
        String logradouro,
        String numero,
        String bairro,
        String localidade,
        String uf,
        String pais
) {
}
