package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestClienteDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseClienteDto;
import br.com.zup.catalisa.APITaxEasy.exception.ClienteNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import br.com.zup.catalisa.APITaxEasy.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ResponseClienteDto> listarTodos() {
        List<ClienteModel> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteModel -> new ResponseClienteDto(
                        clienteModel.getId(),
                        clienteModel.getNome(),
                        clienteModel.getEmail(),
                        clienteModel.getTelefone(),
                        clienteModel.getPedidos()))
                .collect(Collectors.toList());
    }

    public ResponseClienteDto buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteModel -> new ResponseClienteDto(
                        clienteModel.getId(),
                        clienteModel.getNome(),
                        clienteModel.getEmail(),
                        clienteModel.getTelefone(),
                        clienteModel.getPedidos()))
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public ClienteModel requestDtoToCliente(RequestClienteDto requestClienteDto) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(requestClienteDto.nome());
        clienteModel.setEmail(requestClienteDto.email());
        clienteModel.setTelefone(requestClienteDto.telefone());
        clienteModel.setCpf(requestClienteDto.cpf());
        return clienteModel;
    }

    public ResponseClienteDto clienteToResponseDto(ClienteModel clienteModel) {
        ResponseClienteDto responseClienteDto = new ResponseClienteDto();
        responseClienteDto.setId(clienteModel.getId());
        responseClienteDto.setNome(clienteModel.getNome());
        responseClienteDto.setEmail(clienteModel.getEmail());
        responseClienteDto.setTelefone(clienteModel.getTelefone());
        return responseClienteDto;
    }

    public ResponseClienteDto criarCliente(RequestClienteDto requestClienteDto) {
        ClienteModel clienteModel = requestDtoToCliente(requestClienteDto);
        clienteModel = clienteRepository.save(clienteModel);
        return clienteToResponseDto(clienteModel);
    }

    public ResponseClienteDto atualizarCliente(Long id, RequestClienteDto requestClienteDto) {
        Optional<ClienteModel> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            ClienteModel clienteModel = clienteOptional.get();

            if (requestClienteDto.nome() != null && !requestClienteDto.nome().isEmpty()) {
                clienteModel.setNome(requestClienteDto.nome());
            }

            if (requestClienteDto.email() != null && !requestClienteDto.email().isEmpty()) {
                clienteModel.setEmail(requestClienteDto.email());
            }

            if (requestClienteDto.telefone() != null && !requestClienteDto.telefone().isEmpty()) {
                clienteModel.setTelefone(requestClienteDto.telefone());
            }

            if (requestClienteDto.cpf() != null && !requestClienteDto.cpf().isEmpty()) {
                clienteModel.setCpf(requestClienteDto.cpf());
            }

            clienteModel = clienteRepository.save(clienteModel);
            return clienteToResponseDto(clienteModel);
        } else {
            throw new ClienteNaoEncontradoException(id);
        }
    }

    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
}
