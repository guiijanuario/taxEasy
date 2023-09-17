package br.com.zup.catalisa.APITaxEasy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TB_ITENS_PEDIDOS")
public class ItemPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoModel produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoModel pedido;

    private BigDecimal precoUnitario;
    private int quantidade;
}
