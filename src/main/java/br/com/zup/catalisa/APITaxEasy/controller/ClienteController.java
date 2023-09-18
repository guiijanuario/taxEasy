package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.RequestClienteDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseClienteDto;
import br.com.zup.catalisa.APITaxEasy.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Lista todos os clientes", method = "GET")
    @GetMapping
    public ResponseEntity<List<ResponseClienteDto>> listarClientes() {
        List<ResponseClienteDto> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Busca um cliente pelo ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> buscarClientePorId(@PathVariable Long id) {
        ResponseClienteDto cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Cadastra um cliente", method = "POST")
    @PostMapping
    public ResponseEntity<ResponseClienteDto> criarCliente(@RequestBody @Valid RequestClienteDto requestClienteDto) {
        ResponseClienteDto cliente = clienteService.criarCliente(requestClienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Edita um cliente", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> atualizarCliente(@PathVariable Long id, @RequestBody @Valid RequestClienteDto requestClienteDto) {
        ResponseClienteDto cliente = clienteService.atualizarCliente(id, requestClienteDto);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Deleta um cliente", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
