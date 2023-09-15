package br.com.zup.catalisa.APITaxEasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CepRequestDto(
        @Schema(description = "CEP", example = "87000-00")
        String cep
) {
}
