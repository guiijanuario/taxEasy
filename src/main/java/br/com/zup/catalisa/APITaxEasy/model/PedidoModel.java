package br.com.zup.catalisa.APITaxEasy.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "TB_PEDIDOS")
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cep", length = 512)
    private CepModel cepEntrega;

    @Column(name = "numero_casa")
    @Schema(description = "Número da casa", example = "30")
    private String numeroCasa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Schema(description = "ID do cliente", example = "3")
    private ClienteModel cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoModel> produtos;

    private BigDecimal taxaEntrega;

    private BigDecimal valorTotal;
}

