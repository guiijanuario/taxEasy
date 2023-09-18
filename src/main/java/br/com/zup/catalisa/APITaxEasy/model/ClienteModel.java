package br.com.zup.catalisa.APITaxEasy.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;


@Entity
@Data
@Table(name = "TB_CLIENTES")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Column(nullable = false)
    @Schema(description = "Nome", example = "Nome completo")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Informe um email válido")
    @Schema(description = "E-mail", example = "nome@email.com")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Schema(description = "Telefone", example = "(44) 9 9899-999")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O campo CPF é obrigatório")
    @CPF(message = "Informe um CPF válido")
    @Schema(description = "999.999.99-88", example = "999.999.99-88")
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoModel> pedidos;

}
