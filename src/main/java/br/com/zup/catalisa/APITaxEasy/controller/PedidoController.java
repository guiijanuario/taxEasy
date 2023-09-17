package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.RequestPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponsePedidoDto;
import br.com.zup.catalisa.APITaxEasy.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<ResponsePedidoDto>> listarTodosPedidos() {
        List<ResponsePedidoDto> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePedidoDto> buscarPedidoPorId(@PathVariable Long id) {
        ResponsePedidoDto pedido = pedidoService.buscarPorId(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponsePedidoDto> criarPedido(@RequestBody RequestPedidoDto requestPedidoDto) {
        ResponsePedidoDto pedidoCriado = pedidoService.criarPedido(requestPedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
