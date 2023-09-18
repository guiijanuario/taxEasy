package br.com.zup.catalisa.APITaxEasy.dto;


import br.com.zup.catalisa.APITaxEasy.model.RoleModel;

import java.util.List;

public record UsuarioDto(
        String username,
        String password,
        List<RoleModel> roles) {
}
