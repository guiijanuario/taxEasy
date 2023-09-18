package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.*;
import br.com.zup.catalisa.APITaxEasy.repository.CepRepository;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
import br.com.zup.catalisa.APITaxEasy.model.CepModel;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;
@Service
public class BuscaCepService {
    private static final String viaCepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    private static final Logger logger = LoggerFactory.getLogger(BuscaCepService.class);

    @Autowired
    @Value("${token.api.google}")
    private String googleApi;

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private CepValidations cepValidations;

    @Autowired
    SistemaConfiguracaoService sistemaConfiguracaoService;

    public EnderecoResponseDto findCep(CepRequestDto cepString) {
        try {
            logger.debug("Método findCep chamado com CEP: {}", cepString.cep());

            cepValidations.validaCep(cepString.cep());
            cepValidations.removedorDeMascaraCep(cepString.cep());

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(viaCepUrl+cepString.cep()+"/json"))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            CepModel cepModel = gson.fromJson(httpResponse.body(), CepModel.class);

            EnderecoResponseDto enderecoResponseDto = new EnderecoResponseDto(
                    cepModel.getCep(),
                    cepModel.getLogradouro(),
                    cepModel.getComplemento(),
                    cepModel.getBairro(),
                    cepModel.getLocalidade(),
                    cepModel.getUf()
            );

            cepRepository.save(cepModel);

            logger.info("CEP {} encontrado com sucesso", cepString.cep());

            return enderecoResponseDto;

        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao buscar CEP: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public CepModel findCepModel(CepRequestDto cepString) {
        try {
            logger.debug("Método findCepModel chamado com CEP: {}", cepString.cep());

            cepValidations.validaCep(cepString.cep());
            cepValidations.removedorDeMascaraCep(cepString.cep());

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(viaCepUrl+cepString.cep()+"/json"))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            CepModel cepModel = gson.fromJson(httpResponse.body(), CepModel.class);

            cepRepository.save(cepModel);

            logger.info("CEP {} encontrado com sucesso", cepString.cep());

            return cepModel;

        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao buscar CEP: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public String getDistanceBetweenCeps(String destinoCep) {
        try {
            ResponseEmpresaDto responseEmpresaDto = sistemaConfiguracaoService.buscarPorId(1L);

            logger.debug("Método getDistanceBetweenCeps chamado com destinoCep: {}", destinoCep);

            String url = "https://maps.googleapis.com/maps/api/distancematrix/json"
                    + "?origins=" + responseEmpresaDto.getCepOrigem()
                    + "&destinations=" + destinoCep
                    + "&units=imperial"
                    + "&key=" + googleApi;

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                return httpResponse.body();
            } else {
                logger.error("A solicitação falhou com código de status: {}", httpResponse.statusCode());
                throw new RuntimeException("A solicitação falhou com código de status: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao buscar distância entre CEPs: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
