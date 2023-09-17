package br.com.zup.catalisa.APITaxEasy.dto;

import br.com.caelum.stella.bean.validation.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestClienteDto(
        @NotBlank(message = "O campo nome é obrigatório")
        String nome,

        @NotBlank(message = "O campo email é obrigatório")
        @Email(message = "Informe um email válido")
        String email,

        @NotBlank(message = "O campo telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O campo CPF é obrigatório")
        @CPF(message = "Informe um CPF válido")
        String cpf
) {
}
