package br.com.zup.catalisa.APITaxEasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record EnderecoResponseDto(
        @Schema(description = "CEP", example = "87000-00")
        String cep,

        @Schema(description = "Rua/Av", example = "Rua Juruá")
        String logradouro,

        @Schema(description = "Complemento do endereço", example = "Perto de algum lugar")
        String complemento,

        @Schema(description = "Bairro", example = "Jardim Antunes")
        String bairro,

        @Schema(description = "Cidade", example = "Maringá")
        String cidade,

        @Schema(description = "Estado", example = "PR")
        String uf
) {
}
