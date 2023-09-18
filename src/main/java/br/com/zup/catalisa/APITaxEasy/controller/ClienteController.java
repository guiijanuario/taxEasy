package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.RequestClienteDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseClienteDto;
import br.com.zup.catalisa.APITaxEasy.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/clientes", produces = {"application/json"})
@Tag(name = "Feature - Clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Lista todos os clientes", method = "GET")
    @GetMapping
    public ResponseEntity<List<ResponseClienteDto>> listarClientes() {
        logger.debug("Entrou no método listarClientes");
        List<ResponseClienteDto> clientes = clienteService.listarTodos();
        logger.info("Listou {} clientes", clientes.size());
        return ResponseEntity.ok(clientes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Busca um cliente pelo ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> buscarClientePorId(@PathVariable Long id) {
        logger.debug("Método buscarClientePorId chamado com o ID: {}", id);
        ResponseClienteDto cliente = clienteService.buscarPorId(id);

        if (cliente != null) {
            logger.info("Cliente encontrado com ID: {}", id);
            return ResponseEntity.ok(cliente);
        }
        logger.warn("Cliente não encontrado com ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Cadastra um cliente", method = "POST")
    @PostMapping
    public ResponseEntity<ResponseClienteDto> criarCliente(@RequestBody @Valid RequestClienteDto requestClienteDto) {
        logger.debug("Método criarCliente chamado com dados: {}", requestClienteDto);
        ResponseClienteDto cliente = clienteService.criarCliente(requestClienteDto);

        logger.info("Cliente criado com sucesso com ID: {}", cliente.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Edita um cliente", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> atualizarCliente(@PathVariable Long id, @RequestBody @Valid RequestClienteDto requestClienteDto) {
        logger.debug("Método atualizarCliente chamado com ID: {} e dados: {}", id, requestClienteDto);
        ResponseClienteDto cliente = clienteService.atualizarCliente(id, requestClienteDto);
        if (cliente != null) {
            logger.info("Cliente atualizado com sucesso com ID: {}", cliente.getId());
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Deleta um cliente", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluir(id);
        logger.warn("Cliente não encontrado com ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
