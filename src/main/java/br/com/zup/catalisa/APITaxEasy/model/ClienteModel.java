package br.com.zup.catalisa.APITaxEasy.model;

import br.com.caelum.stella.bean.validation.CPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Informe um email válido")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O campo CPF é obrigatório")
    @CPF(message = "Informe um CPF válido")
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PedidoModel> pedidos;
}
