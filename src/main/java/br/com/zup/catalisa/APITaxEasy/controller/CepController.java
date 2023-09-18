package br.com.zup.catalisa.APITaxEasy.controller;
import br.com.zup.catalisa.APITaxEasy.dto.*;
import br.com.zup.catalisa.APITaxEasy.service.BuscaCepService;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/cep", produces = {"application/json"})
@Tag(name = "Feature - Cep")
public class CepController {

    @Autowired
    BuscaCepService buscaCepService;

    @Autowired
    CepValidations cepValidations;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = " : Busca endereço pelo CEP", method = "GET")
    @Cacheable("cep")
    public ResponseEntity<EnderecoResponseDto> buscaCep(@RequestBody CepRequestDto cepRequestDto){
        EnderecoResponseDto enderecoEncontrado = buscaCepService.findCep(cepRequestDto);
        return ResponseEntity.ok(enderecoEncontrado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = " : Calcula a distância entre o cep de origem e o cep do body", method = "GET")
    @GetMapping("/distancia")
    public ResponseEntity<String> calcularDistanciaEntreCeps(@RequestBody DestinoCepDto destinoCepDto) {
        try {
            String resposta = buscaCepService.getDistanceBetweenCeps(destinoCepDto.getDestinoCep());
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao calcular a distância: " + e.getMessage());
        }
    }

}
