package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.UsuarioDto;
import br.com.zup.catalisa.APITaxEasy.model.UsuarioModel;
import br.com.zup.catalisa.APITaxEasy.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioModel> listarTodosUsuarios() {
        try {
            logger.debug("Listando todos os usuários.");

            List<UsuarioModel> usuarios = usuarioRepository.findAll();

            logger.debug("Total de {} usuários encontrados.", usuarios.size());

            return usuarios;
        } catch (Exception e) {
            logger.error("Erro ao listar todos os usuários.", e);
            throw e;
        }
    }

    public UsuarioModel criarNovoUsuario(UsuarioDto usuarioDto) {
        try {
            logger.debug("Criando um novo usuário.");

            validarUsuario(usuarioDto.username());
            validarPassword(usuarioDto.password());

            UsuarioModel novoUsuario = new UsuarioModel();
            novoUsuario.setUsername(usuarioDto.username());
            novoUsuario.setPassword(usuarioDto.password());
            novoUsuario.setRoles(usuarioDto.roles());

            novoUsuario = usuarioRepository.save(novoUsuario);

            logger.debug("Usuário criado com sucesso. ID: {}", novoUsuario.getId_usuario());

            return novoUsuario;
        } catch (Exception e) {
            logger.error("Erro ao criar novo usuário.", e);
            throw e;
        }
    }

    public void deletarUsuario(Long userId) {
        try {
            logger.debug("Deletando usuário pelo ID: {}", userId);

            if (!usuarioRepository.existsById(userId)) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }

            usuarioRepository.deleteById(userId);

            logger.debug("Usuário deletado com sucesso. ID: {}", userId);
        } catch (IllegalArgumentException e) {
            logger.error("Usuário não encontrado ao tentar deletar pelo ID: {}", userId);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário pelo ID: {}", userId, e);
            throw e;
        }
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
