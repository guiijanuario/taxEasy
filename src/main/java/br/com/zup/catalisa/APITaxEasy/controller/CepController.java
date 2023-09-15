package br.com.zup.catalisa.APITaxEasy.controller;
import br.com.zup.catalisa.APITaxEasy.dto.CepRequestDto;
import br.com.zup.catalisa.APITaxEasy.dto.EnderecoResponseDto;
import br.com.zup.catalisa.APITaxEasy.service.BuscaCepService;
import br.com.zup.catalisa.APITaxEasy.validations.CepValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/cep", produces = {"application/json"})
@Tag(name = "Feature - Cep")
public class CepController {

    @Autowired
    BuscaCepService buscaCepService;

    @Autowired
    CepValidations cepValidations;

    @GetMapping
    @Operation(summary = " : Busca endereço pelo CEP", method = "GET")
    @Cacheable("cep")
    public ResponseEntity<EnderecoResponseDto> buscaCep(@RequestBody CepRequestDto cepRequestDto){
        EnderecoResponseDto enderecoEncontrado = buscaCepService.findCep(cepRequestDto);
        return ResponseEntity.ok(enderecoEncontrado);
    }
}
