package br.com.zup.catalisa.APITaxEasy.config;

import br.com.zup.catalisa.APITaxEasy.dto.RequestEmpresaDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseEmpresaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuracoes")
public class SistemaConfiguracaoController {

    @Autowired
    private SistemaConfiguracaoService sistemaConfiguracaoService;

    @PostMapping
    public ResponseEntity<ResponseEmpresaDto> cadastrarEmpresa(@RequestBody RequestEmpresaDto requestEmpresaDto) {
        ResponseEmpresaDto responseEmpresaDto = sistemaConfiguracaoService.cadastrarEmpresa(requestEmpresaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseEmpresaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEmpresaDto> buscarPorId(@PathVariable Long id) {
        ResponseEmpresaDto responseEmpresaDto = sistemaConfiguracaoService.buscarPorId(id);
        return ResponseEntity.ok(responseEmpresaDto);
    }
}