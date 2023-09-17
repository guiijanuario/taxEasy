package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestProdutoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.exception.ProdutoNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.model.ProdutoModel;
import br.com.zup.catalisa.APITaxEasy.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ResponseProdutoDto> listarTodos() {
        List<ProdutoModel> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produtoModel -> new ResponseProdutoDto(
                        produtoModel.getId(),
                        produtoModel.getNome(),
                        produtoModel.getDescricao(),
                        produtoModel.getPreco(),
                        produtoModel.getCategoria()))
                .collect(Collectors.toList());
    }

    public ResponseProdutoDto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(produtoModel -> new ResponseProdutoDto(
                        produtoModel.getId(),
                        produtoModel.getNome(),
                        produtoModel.getDescricao(),
                        produtoModel.getPreco(),
                        produtoModel.getCategoria()))
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public ProdutoModel requestDtoToProduto(RequestProdutoDto requestProdutoDto) {
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(requestProdutoDto.nome());
        produtoModel.setDescricao(requestProdutoDto.descricao());
        produtoModel.setPreco(requestProdutoDto.preco());
        produtoModel.setCategoria(requestProdutoDto.categoria());
        return produtoModel;
    }

    public ResponseProdutoDto produtoToResponseDto (ProdutoModel produtoModel){
        ResponseProdutoDto responseProdutoDto = new ResponseProdutoDto();
        responseProdutoDto.setId(produtoModel.getId());
        responseProdutoDto.setNome(produtoModel.getNome());
        responseProdutoDto.setDescricao(produtoModel.getDescricao());
        responseProdutoDto.setPreco(produtoModel.getPreco());
        responseProdutoDto.setCategoria(produtoModel.getCategoria());
        return responseProdutoDto;
    }

    public ResponseProdutoDto criarProduto(RequestProdutoDto requestProdutoDto) {
        ProdutoModel produtoModel = requestDtoToProduto(requestProdutoDto);
        produtoModel = produtoRepository.save(produtoModel);
        return produtoToResponseDto(produtoModel);
    }


    public ResponseProdutoDto atualizarProduto(Long id, RequestProdutoDto requestProdutoDto) {
        Optional<ProdutoModel> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            ProdutoModel produtoModel = produtoOptional.get();

            if (requestProdutoDto.nome() != null && !requestProdutoDto.nome().isEmpty()) {
                produtoModel.setNome(requestProdutoDto.nome());
            }

            if (requestProdutoDto.descricao() != null && !requestProdutoDto.descricao().isEmpty()) {
                produtoModel.setDescricao(requestProdutoDto.descricao());
            }

            if (requestProdutoDto.preco() != null) {
                produtoModel.setPreco(requestProdutoDto.preco());
            }

            if (requestProdutoDto.categoria() != null && !requestProdutoDto.categoria().isEmpty()) {
                produtoModel.setCategoria(requestProdutoDto.categoria());
            }

            produtoModel = produtoRepository.save(produtoModel);
            return produtoToResponseDto(produtoModel);
        } else {
            throw new ProdutoNaoEncontradoException(id);
        }
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }
}
