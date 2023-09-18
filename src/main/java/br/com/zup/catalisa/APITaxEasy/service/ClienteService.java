package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestClienteDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseClienteDto;
import br.com.zup.catalisa.APITaxEasy.exception.ClienteNaoEncontradoException;
import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import br.com.zup.catalisa.APITaxEasy.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ResponseClienteDto> listarTodos() {
        logger.debug("Método listarTodos chamado.");
        List<ClienteModel> clientes = clienteRepository.findAll();
        logger.info("Número de clientes encontrados: {}", clientes.size());
        return clientes.stream()
                .map(clienteModel -> new ResponseClienteDto(
                        clienteModel.getId(),
                        clienteModel.getNome(),
                        clienteModel.getEmail(),
                        clienteModel.getTelefone()))
                .collect(Collectors.toList());
    }

    public ResponseClienteDto buscarPorId(Long id) {
        logger.debug("Método buscarPorId chamado com ID: {}", id);
        return clienteRepository.findById(id)
                .map(clienteModel -> new ResponseClienteDto(
                        clienteModel.getId(),
                        clienteModel.getNome(),
                        clienteModel.getEmail(),
                        clienteModel.getTelefone()))
                .orElseThrow(() -> {
                    logger.error("Cliente com ID {} não encontrado. Lançando exceção.", id);
                    return new ClienteNaoEncontradoException(id);
                });
    }
    public ClienteModel requestDtoToCliente(RequestClienteDto requestClienteDto) {
        logger.debug("Convertendo RequestClienteDto para ClienteModel.");
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(requestClienteDto.nome());
        clienteModel.setEmail(requestClienteDto.email());
        clienteModel.setTelefone(requestClienteDto.telefone());
        clienteModel.setCpf(requestClienteDto.cpf());
        return clienteModel;
    }

    public ResponseClienteDto clienteToResponseDto(ClienteModel clienteModel) {
        logger.debug("Convertendo ClienteModel para ResponseClienteDto.");
        ResponseClienteDto responseClienteDto = new ResponseClienteDto();
        responseClienteDto.setId(clienteModel.getId());
        responseClienteDto.setNome(clienteModel.getNome());
        responseClienteDto.setEmail(clienteModel.getEmail());
        responseClienteDto.setTelefone(clienteModel.getTelefone());
        return responseClienteDto;
    }

    public ResponseClienteDto criarCliente(RequestClienteDto requestClienteDto) {
        logger.debug("Criando novo cliente.");
        ClienteModel clienteModel = requestDtoToCliente(requestClienteDto);
        clienteModel = clienteRepository.save(clienteModel);
        logger.info("Cliente criado com sucesso. ID: {}", clienteModel.getId());
        return clienteToResponseDto(clienteModel);
    }

    public ResponseClienteDto atualizarCliente(Long id, RequestClienteDto requestClienteDto) {
        logger.debug("Atualizando cliente com ID: {}", id);
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
            logger.info("Cliente atualizado com sucesso. ID: {}", clienteModel.getId());
            clienteModel = clienteRepository.save(clienteModel);
            return clienteToResponseDto(clienteModel);
        } else {
            logger.error("Cliente com ID {} não encontrado. Lançando exceção.", id);
            throw new ClienteNaoEncontradoException(id);
        }
    }

    public void excluir(Long id) {
        logger.debug("Excluindo cliente com ID: {}", id);
        clienteRepository.deleteById(id);
        logger.info("Cliente excluído com sucesso. ID: {}", id);
    }
}
