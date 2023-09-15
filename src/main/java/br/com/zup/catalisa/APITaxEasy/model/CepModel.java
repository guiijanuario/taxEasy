package br.com.zup.catalisa.APITaxEasy.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(name = "Endereço")
public class CepModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "CEP", example = "87000-00")
    private String cep;

    @Schema(description = "Rua/Av", example = "Rua Juruá")
    private String logradouro;

    @Schema(description = "Complemento do endereço", example = "Ponto de referência")
    private String complemento;

    @Schema(description = "Bairro", example = "Jardim Antunes")
    private String bairro;

    @Schema(description = "Cidade", example = "Maringá")
    private String localidade;

    @Schema(description = "Estado", example = "PR")
    private String uf;

    @Schema(description = "Número do IBGE", example = "3550308")
    private String ibge;

    @Schema(description = "Guia de Informação e Apuração do ICMS", example = "1004")
    private String gia;

    @Schema(description = "Código de área", example = "44")
    private String ddd;

    @Schema(description = "Sistema Integrado de Administração Financeira do Governo Federal", example = "7107")
    private String siafi;
}
