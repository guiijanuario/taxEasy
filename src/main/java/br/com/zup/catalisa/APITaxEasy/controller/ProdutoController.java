package br.com.zup.catalisa.APITaxEasy.controller;


import br.com.zup.catalisa.APITaxEasy.dto.RequestProdutoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ResponseProdutoDto>> listarTodos() {
        List<ResponseProdutoDto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> buscarPorId(@PathVariable Long id) {
        ResponseProdutoDto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponseProdutoDto> criarProduto(@RequestBody RequestProdutoDto requestProdutoDto) {
        ResponseProdutoDto produto = produtoService.criarProduto(requestProdutoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProdutoDto> atualizarProduto(@PathVariable Long id, @RequestBody RequestProdutoDto requestProdutoDto) {
        ResponseProdutoDto produto = produtoService.atualizarProduto(id, requestProdutoDto);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
