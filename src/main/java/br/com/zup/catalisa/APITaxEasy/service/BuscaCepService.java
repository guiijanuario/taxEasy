package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.config.SistemaConfiguracaoService;
import br.com.zup.catalisa.APITaxEasy.dto.*;
import br.com.zup.catalisa.APITaxEasy.repository.CepRepository;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
import br.com.zup.catalisa.APITaxEasy.model.CepModel;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;
@Service
public class BuscaCepService {
    private static final String viaCepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private CepValidations cepValidations;

    @Autowired
    SistemaConfiguracaoService sistemaConfiguracaoService;

    public EnderecoResponseDto findCep(CepRequestDto cepString) {
        try {

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

            return enderecoResponseDto;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public CepModel findCepModel(CepRequestDto cepString) {
        try {

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

            return cepModel;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public String getDistanceBetweenCeps(String destinoCep) {
        try {
            ResponseEmpresaDto responseEmpresaDto = sistemaConfiguracaoService.buscarPorId(1L);

            String url = "https://maps.googleapis.com/maps/api/distancematrix/json"
                    + "?origins=" + responseEmpresaDto.getCepOrigem()
                    + "&destinations=" + destinoCep
                    + "&units=imperial"
                    + "&key=AIzaSyAlf_lqH3R8cR_4AWVnKpsNjGGk-ZSzAPA";

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String responseJson = httpResponse.body();
                return responseJson;
            } else {
                throw new RuntimeException("A solicitação falhou com código de status: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
