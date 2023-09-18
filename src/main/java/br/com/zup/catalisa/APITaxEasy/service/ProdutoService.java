package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestProdutoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.exception.ProdutoNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.exception.ProdutosNaoEncontradosException;
import br.com.zup.catalisa.APITaxEasy.model.ProdutoModel;
import br.com.zup.catalisa.APITaxEasy.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ResponseProdutoDto> listarTodos() {
        try {
            logger.debug("Listando todos os produtos.");

            List<ProdutoModel> produtos = produtoRepository.findAll();
            List<ResponseProdutoDto> responseProdutos = produtos.stream()
                    .map(produtoModel -> new ResponseProdutoDto(
                            produtoModel.getId(),
                            produtoModel.getNome(),
                            produtoModel.getDescricao(),
                            produtoModel.getPreco(),
                            produtoModel.getCategoria()))
                    .collect(Collectors.toList());

            logger.debug("Produtos listados com sucesso.");

            return responseProdutos;
        } catch (ProdutosNaoEncontradosException e) {
            logger.error("Erro ao listar produtos.", e);
            throw e;
        }
    }

    public ResponseProdutoDto buscarPorId(Long id) {
        try {
            logger.debug("Buscando produto pelo ID: {}", id);

            return produtoRepository.findById(id)
                    .map(produtoModel -> new ResponseProdutoDto(
                            produtoModel.getId(),
                            produtoModel.getNome(),
                            produtoModel.getDescricao(),
                            produtoModel.getPreco(),
                            produtoModel.getCategoria()))
                    .orElseThrow(() -> {
                        logger.error("Produto com ID {} não encontrado.", id);
                        return new ProdutoNaoEncontradoException(id);
                    });
        } catch (ProdutoNaoEncontradoException e) {
            logger.error("Produto com ID {} não encontrado.", id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao buscar produto pelo ID: {}", id, e);
            throw e;
        }
    }

    public ProdutoModel requestDtoToProduto(RequestProdutoDto requestProdutoDto) {
        try {
            logger.debug("Convertendo RequestProdutoDto para ProdutoModel.");

            ProdutoModel produtoModel = new ProdutoModel();
            produtoModel.setNome(requestProdutoDto.nome());
            produtoModel.setDescricao(requestProdutoDto.descricao());
            produtoModel.setPreco(requestProdutoDto.preco());
            produtoModel.setCategoria(requestProdutoDto.categoria());

            logger.debug("RequestProdutoDto convertido com sucesso.");

            return produtoModel;
        } catch (Exception e) {
            logger.error("Erro ao converter RequestProdutoDto para ProdutoModel.", e);
            throw e;
        }
    }

    public ResponseProdutoDto produtoToResponseDto(ProdutoModel produtoModel) {
        try {
            logger.debug("Convertendo ProdutoModel para ResponseProdutoDto.");

            ResponseProdutoDto responseProdutoDto = new ResponseProdutoDto();
            responseProdutoDto.setId(produtoModel.getId());
            responseProdutoDto.setNome(produtoModel.getNome());
            responseProdutoDto.setDescricao(produtoModel.getDescricao());
            responseProdutoDto.setPreco(produtoModel.getPreco());
            responseProdutoDto.setCategoria(produtoModel.getCategoria());

            logger.debug("ProdutoModel convertido para ResponseProdutoDto com sucesso.");

            return responseProdutoDto;
        } catch (Exception e) {
            logger.error("Erro ao converter ProdutoModel para ResponseProdutoDto.", e);
            throw e;
        }
    }

    public ResponseProdutoDto criarProduto(RequestProdutoDto requestProdutoDto) {
        try {
            logger.debug("Criando um novo produto.");

            ProdutoModel produtoModel = requestDtoToProduto(requestProdutoDto);
            produtoModel = produtoRepository.save(produtoModel);

            logger.debug("Produto criado com sucesso. ID: {}", produtoModel.getId());

            return produtoToResponseDto(produtoModel);
        } catch (Exception e) {
            logger.error("Erro ao criar produto.", e);
            throw e;
        }
    }

    public ResponseProdutoDto atualizarProduto(Long id, RequestProdutoDto requestProdutoDto) {
        try {
            logger.debug("Atualizando produto pelo ID: {}", id);

            Optional<ProdutoModel> produtoOptional = produtoRepository.findById(id);
            if (produtoOptional.isPresent()) {
                ProdutoModel produtoModel = produtoOptional.get();

                if (requestProdutoDto.nome() != null && !requestProdutoDto.nome().isEmpty()) {
                    produtoModel.setNome(requestProdutoDto.nome());
                }

                if (requestProdutoDto.descricao() != null && !requestProdutoDto.descricao().isEmpty()) {
                    produtoModel.setDescricao(requestProdutoDto.descricao());
                }

                if (requestProdutoDto.categoria() != null && !requestProdutoDto.categoria().isEmpty()) {
                    produtoModel.setCategoria(requestProdutoDto.categoria());
                }

                produtoModel = produtoRepository.save(produtoModel);

                logger.debug("Produto atualizado com sucesso. ID: {}", produtoModel.getId());

                return produtoToResponseDto(produtoModel);
            } else {
                logger.error("Produto com ID {} não encontrado.", id);
                throw new ProdutoNaoEncontradoException(id);
            }
        } catch (ProdutoNaoEncontradoException e) {
            logger.error("Produto com ID {} não encontrado.", id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao atualizar produto pelo ID: {}", id, e);
            throw e;
        }
    }

    public void excluir(Long id) {
        try {
            logger.debug("Excluindo produto pelo ID: {}", id);

            produtoRepository.deleteById(id);

            logger.debug("Produto excluído com sucesso. ID: {}", id);
        } catch (ProdutoNaoEncontradoException e) {
            logger.error("Erro ao excluir produto pelo ID: {}", id, e);
            throw e;
        }
    }
}
