package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.RequestClienteDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseClienteDto;
import br.com.zup.catalisa.APITaxEasy.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ResponseClienteDto>> listarClientes() {
        List<ResponseClienteDto> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> buscarClientePorId(@PathVariable Long id) {
        ResponseClienteDto cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponseClienteDto> criarCliente(@Valid @RequestBody RequestClienteDto requestClienteDto) {
        ResponseClienteDto cliente = clienteService.criarCliente(requestClienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> atualizarCliente(@PathVariable Long id, @Valid @RequestBody RequestClienteDto requestClienteDto) {
        ResponseClienteDto cliente = clienteService.atualizarCliente(id, requestClienteDto);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
