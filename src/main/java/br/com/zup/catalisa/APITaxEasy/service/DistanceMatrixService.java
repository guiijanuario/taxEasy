package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.CepRequestDto;
import br.com.zup.catalisa.APITaxEasy.model.CepModel;
import br.com.zup.catalisa.APITaxEasy.repository.CepRepository;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
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
public class DistanceMatrixService {
    private static final String viaCepUrl = "https://api.distancematrix.ai/maps/api/distancematrix/json?origins=Rua+Gaivota+11,Jardim+Ol%C3%ADmpico,Maring%C3%A1,PR,87070-450,Brasil&destinations=Avenida+Pioneiro+Raul+Ambr%C3%B3sio+Valente+30,Jardim+Am%C3%A9rica,Maring%C3%A1,PR,87045-440,Brasil&key=jzawP1WU89OnxCbNkQyAeIj9Honmm";
    private static final Gson gson = new Gson();

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private  CepValidations cepValidations;

    public CepModel findCep(CepRequestDto cepString) {

        try {
            cepValidations.validaCep(cepString.cep());

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                   // .uri(URI.create(viaCepUrl+cepString+"/json"))
                    .uri(URI.create("http://api.distancematrix.ai/maps/api/distancematrix/json?origins=Rua+Gaivota+11,Jardim+Ol%C3%ADmpico,Maring%C3%A1,PR,87070-450,Brasil&destinations=Avenida+Pioneiro+Raul+Ambr%C3%B3sio+Valente+30,Jardim+Am%C3%A9rica,Maring%C3%A1,PR,87045-440,Brasil&key=jzawP1WU89OnxCbNkQyAeIj9Honmm"))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            CepModel cepModel = gson.fromJson(httpResponse.body(), CepModel.class);

            return cepRepository.save(cepModel);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
