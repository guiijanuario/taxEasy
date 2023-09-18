package br.com.zup.catalisa.APITaxEasy.controller;


import br.com.zup.catalisa.APITaxEasy.dto.RequestProdutoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Autowired
    private ProdutoService produtoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Lista todos os Produtos", method = "GET")
    @GetMapping
    public ResponseEntity<List<ResponseProdutoDto>> listarTodos() {
        List<ResponseProdutoDto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Lista um produto especifico pelo ID", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> buscarPorId(@PathVariable Long id) {
        ResponseProdutoDto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Cadastra um produto", method = "POST")
    @PostMapping
    public ResponseEntity<ResponseProdutoDto> criarProduto(@RequestBody RequestProdutoDto requestProdutoDto) {
        ResponseProdutoDto produto = produtoService.criarProduto(requestProdutoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Altera um produto", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> atualizarProduto(@PathVariable Long id, @RequestBody RequestProdutoDto requestProdutoDto) {
        ResponseProdutoDto produto = produtoService.atualizarProduto(id, requestProdutoDto);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Deleta um produto", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
