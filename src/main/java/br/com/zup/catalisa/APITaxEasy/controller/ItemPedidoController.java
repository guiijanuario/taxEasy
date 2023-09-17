package br.com.zup.catalisa.APITaxEasy.controller;

import br.com.zup.catalisa.APITaxEasy.dto.RequestItemPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseItemPedidoDto;
import br.com.zup.catalisa.APITaxEasy.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<List<ResponseItemPedidoDto>> listarTodosItensPedido() {
        List<ResponseItemPedidoDto> itensPedido = itemPedidoService.listarTodos();
        return ResponseEntity.ok(itensPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseItemPedidoDto> buscarItemPedidoPorId(@PathVariable Long id) {
        ResponseItemPedidoDto itemPedido = itemPedidoService.buscarPorId(id);
        if (itemPedido != null) {
            return ResponseEntity.ok(itemPedido);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponseItemPedidoDto> criarItemPedido(@RequestBody RequestItemPedidoDto requestItemPedidoDto) {
        ResponseItemPedidoDto itemPedidoCriado = itemPedidoService.criarItemPedido(requestItemPedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseItemPedidoDto> atualizarItemPedido(@PathVariable Long id, @RequestBody RequestItemPedidoDto requestItemPedidoDto) {
        ResponseItemPedidoDto itemPedidoAtualizado = itemPedidoService.atualizarItemPedido(id, requestItemPedidoDto);
        if (itemPedidoAtualizado != null) {
            return ResponseEntity.ok(itemPedidoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirItemPedido(@PathVariable Long id) {
        itemPedidoService.excluirItemPedido(id);
        return ResponseEntity.noContent().build();
    }
}
