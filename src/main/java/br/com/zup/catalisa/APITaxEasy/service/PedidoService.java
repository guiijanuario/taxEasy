package br.com.zup.catalisa.APITaxEasy.service;


import br.com.zup.catalisa.APITaxEasy.dto.RequestPedidoDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponsePedidoDto;
import br.com.zup.catalisa.APITaxEasy.model.PedidoModel;
import br.com.zup.catalisa.APITaxEasy.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<ResponsePedidoDto> listarTodos() {
        List<PedidoModel> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::pedidoToResponseDto)
                .collect(Collectors.toList());
    }

    public ResponsePedidoDto buscarPorId(Long id) {
        Optional<PedidoModel> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            PedidoModel pedidoModel = pedidoOptional.get();
            return pedidoToResponseDto(pedidoModel);
        }
        return null;
    }

    public PedidoModel requestDtoToPedido(RequestPedidoDto requestPedidoDto) {
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setCliente(requestPedidoDto.cliente());
        pedidoModel.setItens(requestPedidoDto.itens());
        pedidoModel.setValorTotal(requestPedidoDto.valorTotal());
        return pedidoModel;
    }

    public ResponsePedidoDto pedidoToResponseDto(PedidoModel pedidoModel) {
        ResponsePedidoDto responsePedidoDto = new ResponsePedidoDto();
        responsePedidoDto.setId(pedidoModel.getId());
        responsePedidoDto.setCliente(pedidoModel.getCliente());
        responsePedidoDto.setItens(pedidoModel.getItens());
        responsePedidoDto.setValorTotal(pedidoModel.getValorTotal());
        return responsePedidoDto;
    }

    public ResponsePedidoDto criarPedido(RequestPedidoDto requestPedidoDto) {
        PedidoModel pedidoModel = requestDtoToPedido(requestPedidoDto);
        pedidoModel = pedidoRepository.save(pedidoModel);
        return pedidoToResponseDto(pedidoModel);
    }

    public void excluir(Long id) {
        pedidoRepository.deleteById(id);
    }
}
