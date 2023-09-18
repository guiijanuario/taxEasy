package br.com.zup.catalisa.APITaxEasy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseDistanciaDto {

    @JsonProperty("distancia_em_km")
    private BigDecimal distanciaEmKm;
}
