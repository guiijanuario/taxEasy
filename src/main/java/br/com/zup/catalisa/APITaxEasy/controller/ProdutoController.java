package br.com.zup.catalisa.APITaxEasy.controller;


import br.com.zup.catalisa.APITaxEasy.dto.RequestProdutoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/produtos", produces = {"application/json"})
@Tag(name = "Feature - Produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = " : Lista todos os Produtos", method = "GET")
    @GetMapping
    public ResponseEntity<List<ResponseProdutoDto>> listarTodos() {
        logger.debug("Método listarTodos chamado.");
        List<ResponseProdutoDto> produtos = produtoService.listarTodos();
        logger.info("Total de produtos encontrados: {}", produtos.size());
        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = " : Lista um produto especifico pelo ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> buscarPorId(@PathVariable Long id) {
        logger.debug("Método buscarPorId chamado com ID: {}", id);
        ResponseProdutoDto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = " : Cadastra um produto", method = "POST")
    @PostMapping
    public ResponseEntity<ResponseProdutoDto> criarProduto(@RequestBody RequestProdutoDto requestProdutoDto) {
        logger.debug("Método criarProduto chamado com dados: {}", requestProdutoDto);
        ResponseProdutoDto produto = produtoService.criarProduto(requestProdutoDto);

        logger.info("Produto criado com sucesso com ID: {}", produto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Altera um produto", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> atualizarProduto(@PathVariable Long id, @RequestBody RequestProdutoDto requestProdutoDto) {
        logger.debug("Método atualizarProduto chamado com ID: {} e dados: {}", id, requestProdutoDto);

        ResponseProdutoDto produto = produtoService.atualizarProduto(id, requestProdutoDto);
        if (produto != null) {
            logger.info("Produto atualizado com sucesso com ID: {}", id);
            return ResponseEntity.ok(produto);
        }
        logger.info("Produto não encontrado para atualização com ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Deleta um produto", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        logger.debug("Método excluir chamado com ID: {}", id);
        produtoService.excluir(id);

        logger.info("Produto excluído com sucesso com ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
