package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestItemPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseItemPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponsePedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseProdutoDto;
import br.com.zup.catalisa.APITaxEasy.exception.ItemPedidoNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.model.ItemPedidoModel;
import br.com.zup.catalisa.APITaxEasy.model.PedidoModel;
import br.com.zup.catalisa.APITaxEasy.model.ProdutoModel;
import br.com.zup.catalisa.APITaxEasy.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ResponseItemPedidoDto> listarTodos() {
        List<ItemPedidoModel> itensPedido = itemPedidoRepository.findAll();
        return itensPedido.stream()
                .map(this::itemPedidoToResponseDto)
                .collect(Collectors.toList());
    }

    public ResponseItemPedidoDto buscarPorId(Long id) {
        Optional<ItemPedidoModel> itemPedidoOptional = itemPedidoRepository.findById(id);
        if (itemPedidoOptional.isPresent()) {
            ItemPedidoModel itemPedidoModel = itemPedidoOptional.get();
            return itemPedidoToResponseDto(itemPedidoModel);
        }
        return null;
    }

    public ResponseItemPedidoDto criarItemPedido(RequestItemPedidoDto requestItemPedidoDto) {
        ItemPedidoModel itemPedidoModel = requestDtoToItemPedido(requestItemPedidoDto);
        itemPedidoModel = itemPedidoRepository.save(itemPedidoModel);
        return itemPedidoToResponseDto(itemPedidoModel);
    }

    public ResponseItemPedidoDto atualizarItemPedido(Long id, RequestItemPedidoDto requestItemPedidoDto) {
        Optional<ItemPedidoModel> itemPedidoOptional = itemPedidoRepository.findById(id);
        if (itemPedidoOptional.isPresent()) {
            ItemPedidoModel itemPedidoModel = itemPedidoOptional.get();

            itemPedidoModel = itemPedidoRepository.save(itemPedidoModel);
            return itemPedidoToResponseDto(itemPedidoModel);
        } else {
            throw new ItemPedidoNaoEncontradoException(id);
        }
    }

    public void excluirItemPedido(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    private ItemPedidoModel requestDtoToItemPedido(RequestItemPedidoDto requestItemPedidoDto) {
        ItemPedidoModel itemPedidoModel = new ItemPedidoModel();
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(requestItemPedidoDto.produto().nome());
        produtoModel.setCategoria(requestItemPedidoDto.produto().descricao());
        produtoModel.setCategoria(requestItemPedidoDto.produto().categoria());
        produtoModel.setPreco(requestItemPedidoDto.produto().preco());

        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setCliente(requestItemPedidoDto.pedido().cliente());
        pedidoModel.setCliente(requestItemPedidoDto.pedido().cliente());

        itemPedidoModel.setProduto(produtoModel);
        itemPedidoModel.setPedido(pedidoModel);
        itemPedidoModel.setPrecoUnitario(requestItemPedidoDto.precoUnitario());
        itemPedidoModel.setQuantidade(requestItemPedidoDto.quantidade());

        return itemPedidoModel;
    }

    private ResponseItemPedidoDto itemPedidoToResponseDto(ItemPedidoModel itemPedidoModel) {
        ResponseItemPedidoDto responseItemPedidoDto = new ResponseItemPedidoDto();

        responseItemPedidoDto.setId(itemPedidoModel.getId());

        ResponseProdutoDto responseProdutoDto = new ResponseProdutoDto();
        responseProdutoDto.setNome(itemPedidoModel.getProduto().getNome());
        responseProdutoDto.setDescricao(itemPedidoModel.getProduto().getDescricao());
        responseProdutoDto.setCategoria(itemPedidoModel.getProduto().getCategoria());
        responseProdutoDto.setPreco(itemPedidoModel.getProduto().getPreco());

        ResponsePedidoDto responsePedidoDto = new ResponsePedidoDto();
        responsePedidoDto.setCliente(itemPedidoModel.getPedido().getCliente());

        responseItemPedidoDto.setProduto(responseProdutoDto);
        responseItemPedidoDto.setPedido(responsePedidoDto);
        responseItemPedidoDto.setPrecoUnitario(itemPedidoModel.getPrecoUnitario());
        responseItemPedidoDto.setQuantidade(itemPedidoModel.getQuantidade());

        return responseItemPedidoDto;
    }
}


