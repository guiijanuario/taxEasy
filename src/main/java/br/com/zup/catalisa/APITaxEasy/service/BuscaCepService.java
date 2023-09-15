package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.CepRequestDto;
import br.com.zup.catalisa.APITaxEasy.dto.EnderecoResponseDto;
import br.com.zup.catalisa.APITaxEasy.repository.CepRepository;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
import br.com.zup.catalisa.APITaxEasy.model.CepModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private CepValidations cepValidations;

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
}
