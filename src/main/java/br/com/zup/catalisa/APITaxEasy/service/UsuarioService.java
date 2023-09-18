package br.com.zup.catalisa.APITaxEasy.service;


import br.com.zup.catalisa.APITaxEasy.dto.UsuarioDto;
import br.com.zup.catalisa.APITaxEasy.model.UsuarioModel;
import br.com.zup.catalisa.APITaxEasy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioModel> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel criarNovoUsuario(UsuarioDto usuarioDto) {
        validarUsuario(usuarioDto.username());
        validarPassword(usuarioDto.password());

        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setUsername(usuarioDto.username());
        novoUsuario.setPassword(usuarioDto.password());
        novoUsuario.setRoles(usuarioDto.roles());

        return usuarioRepository.save(novoUsuario);
    }

    public void deletarUsuario(Long userId) {
        if (!usuarioRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(userId);
    }

    private void validarUsuario(String username) {
        if (username == null || username.length() < 6 || !username.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Nome de usuário inválido.");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.length() < 8 || !password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).*$")) {
            throw new IllegalArgumentException("Senha inválida.");
        }
    }


}
