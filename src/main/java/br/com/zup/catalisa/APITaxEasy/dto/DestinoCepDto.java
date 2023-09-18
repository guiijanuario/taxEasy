package br.com.zup.catalisa.APITaxEasy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DestinoCepDto {
    @JsonProperty("destino_cep")
    private String destinoCep;
}
