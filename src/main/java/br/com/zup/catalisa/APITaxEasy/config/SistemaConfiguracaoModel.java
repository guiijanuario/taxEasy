package br.com.zup.catalisa.APITaxEasy.config;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "TB_SISTEMA_CONFIGURACAO")
public class SistemaConfiguracaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nomeEmpresa;
    @Column(nullable = false)
    private String cnpj;
    @Column(nullable = false)
    private String cepOrigem;
    @Column(nullable = false)
    private String numeroOrigem;
    @Column(nullable = false)
    private BigDecimal cursoEntrega;

}
