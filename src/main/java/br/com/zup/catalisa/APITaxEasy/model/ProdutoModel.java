package br.com.zup.catalisa.APITaxEasy.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "TB_PRODUTOS")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "Nome do produto", example = "Biscoito de Maisena")
    @Column(name = "nome")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Detalhes do produto")
    @Column(name = "descricao")
    private String descricao;

    @Schema(description = "Preço do produto", example = "10.30")
    @Column(name = "preco")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Limpeza")
    @Column(name = "categoria")
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoModel pedido;

}
