package br.com.zup.catalisa.APITaxEasy.controller;


import br.com.zup.catalisa.APITaxEasy.dto.RequestPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponsePedidoDto;
import br.com.zup.catalisa.APITaxEasy.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pedidos", produces = {"application/json"})
@Tag(name = "Feature - Pedidos")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

//    @GetMapping("/{id}")
//    public ResponseEntity<ResponsePedidoDto> buscarPorId(@PathVariable Long id) {
//        ResponsePedidoDto pedido = pedidoService.buscarPorId(id);
//        if (pedido != null) {
//            return ResponseEntity.ok(pedido);
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = " : Realiza um pedido", method = "POST")
    @PostMapping
    public ResponseEntity<ResponsePedidoDto> criarPedido(@RequestBody RequestPedidoDto requestPedidoDto) {
        logger.debug("Método criarPedido chamado com dados: {}", requestPedidoDto);
        ResponsePedidoDto pedido = pedidoService.criarPedido(requestPedidoDto);

        logger.info("Pedido criado com sucesso com ID: {}", pedido.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ResponsePedidoDto> atualizarPedido(@PathVariable Long id, @RequestBody RequestPedidoDto requestPedidoDto) {
//        ResponsePedidoDto pedido = pedidoService.atualizarPedido(id, requestPedidoDto);
//        if (pedido != null) {
//            return ResponseEntity.ok(pedido);
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        pedidoService.excluir(id);
//        return ResponseEntity.noContent().build();
//    }
}
