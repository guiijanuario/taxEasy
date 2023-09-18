package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.*;
import br.com.zup.catalisa.APITaxEasy.exception.ClienteNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.exception.ProdutoNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.model.CepModel;
import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import br.com.zup.catalisa.APITaxEasy.model.PedidoModel;
import br.com.zup.catalisa.APITaxEasy.model.ProdutoModel;
import br.com.zup.catalisa.APITaxEasy.repository.ClienteRepository;
import br.com.zup.catalisa.APITaxEasy.repository.PedidoRepository;
import br.com.zup.catalisa.APITaxEasy.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private BuscaCepService buscaCepService;

    @Autowired
    private SistemaConfiguracaoService sistemaConfiguracaoService;


//    public ResponsePedidoDto criarPedido(RequestPedidoDto requestPedidoDto) throws ClienteNaoEncontradoException {
//        logger.debug("Criando novo pedido.");
//        ClienteModel cliente = clienteRepository.findById(requestPedidoDto.clienteId())
//                .orElseThrow(() -> new ClienteNaoEncontradoException(requestPedidoDto.clienteId()));
//
//        CepRequestDto cepRequestDto = new CepRequestDto(requestPedidoDto.cepEntrega());
//        CepModel cepEntrega = buscaCepService.findCepModel(cepRequestDto);
//
//        PedidoModel pedido = new PedidoModel();
//        pedido.setCepEntrega(cepEntrega);
//        pedido.setNumeroCasa(requestPedidoDto.numeroCasaEntrega());
//        pedido.setCliente(cliente);
//
//        List<ProdutoModel> produtos = new ArrayList<>();
//
//        for (Long produtoId : requestPedidoDto.produtosIds()) {
//            ProdutoModel produto = produtoRepository.findById(produtoId)
//                    .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
//            produtos.add(produto);
//        }
//
//        pedido.setProdutos(produtos);
//
//        BigDecimal valorTotal = calcularValorTotal(produtos);
//        ResponseEmpresaDto sistemaConfiguracao = sistemaConfiguracaoService.buscarPorId(1L);
//        pedido.setTaxaEntrega(sistemaConfiguracao.getCustoEntrega());
//        pedido.setValorTotal(valorTotal);
//
//        pedido = pedidoRepository.save(pedido);
//
//        EnderecoResponseDto enderecoResponseDto = buscaCepService.findCep(cepRequestDto);
//
//        List<ResponseProdutoDto> produtosDto = produtos.stream()
//                .map(produto -> new ResponseProdutoDto(
//                        produto.getId(),
//                        produto.getNome(),
//                        produto.getDescricao(),
//                        produto.getPreco(),
//                        produto.getCategoria()
//                ))
//                .collect(Collectors.toList());
//        logger.info("Pedido criado com sucesso. ID: {}", pedido.getId());
//        return new ResponsePedidoDto(
//                pedido.getId(),
//                produtosDto,
//                enderecoResponseDto,
//                pedido.getNumeroCasa(),
//                pedido.getCliente().getNome(),
//                pedido.getTaxaEntrega(),
//                pedido.getValorTotal()
//        );
//    }


    public ResponsePedidoDto criarPedido(RequestPedidoDto requestPedidoDto) throws ClienteNaoEncontradoException {
        try {
            logger.debug("Criando novo pedido.");

            ClienteModel cliente = clienteRepository.findById(requestPedidoDto.clienteId())
                    .orElseThrow(() -> new ClienteNaoEncontradoException(requestPedidoDto.clienteId()));

            CepRequestDto cepRequestDto = new CepRequestDto(requestPedidoDto.cepEntrega());
            CepModel cepEntrega = buscaCepService.findCepModel(cepRequestDto);

            PedidoModel pedido = new PedidoModel();
            pedido.setCepEntrega(cepEntrega);
            pedido.setNumeroCasa(requestPedidoDto.numeroCasaEntrega());
            pedido.setCliente(cliente);

            List<ProdutoModel> produtos = new ArrayList<>();

            for (Long produtoId : requestPedidoDto.produtosIds()) {
                ProdutoModel produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
                produtos.add(produto);
            }

            pedido.setProdutos(produtos);

            BigDecimal valorTotal = calcularValorTotal(produtos);
            ResponseEmpresaDto sistemaConfiguracao = sistemaConfiguracaoService.buscarPorId(1L);
            pedido.setTaxaEntrega(sistemaConfiguracao.getCustoEntrega());
            pedido.setValorTotal(valorTotal);

            pedido = pedidoRepository.save(pedido);

            EnderecoResponseDto enderecoResponseDto = buscaCepService.findCep(cepRequestDto);

            List<ResponseProdutoDto> produtosDto = produtos.stream()
                    .map(produto -> new ResponseProdutoDto(
                            produto.getId(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getCategoria()
                    ))
                    .collect(Collectors.toList());
            logger.info("Pedido criado com sucesso. ID: {}", pedido.getId());

            return new ResponsePedidoDto(
                    pedido.getId(),
                    produtosDto,
                    enderecoResponseDto,
                    pedido.getNumeroCasa(),
                    pedido.getCliente().getNome(),
                    pedido.getTaxaEntrega(),
                    pedido.getValorTotal()
            );
        } catch (ClienteNaoEncontradoException e) {
            logger.error("Erro ao criar pedido. Cliente com ID {} não encontrado.", requestPedidoDto.clienteId());
            throw e;
        } catch (ProdutoNaoEncontradoException e) {
            logger.error("Erro ao criar pedido. Produto com ID {} não encontrado.", requestPedidoDto.produtosIds());
            throw e;
        }
    }

    private BigDecimal calcularValorTotal(List<ProdutoModel> produtos) {
        logger.debug("Calculando o valor total dos produtos.");

        BigDecimal valorTotal = produtos.stream()
                .map(ProdutoModel::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.debug("Valor total calculado: {}", valorTotal);
        return valorTotal;
    }
}
