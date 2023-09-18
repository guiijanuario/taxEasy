package br.com.zup.catalisa.APITaxEasy.service;

import br.com.zup.catalisa.APITaxEasy.dto.RequestEmpresaDto;
import br.com.zup.catalisa.APITaxEasy.dto.ResponseEmpresaDto;
import br.com.zup.catalisa.APITaxEasy.exception.EmpresaNaoEncontradaException;
import br.com.zup.catalisa.APITaxEasy.model.SistemaConfiguracaoModel;
import br.com.zup.catalisa.APITaxEasy.repository.SistemaConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SistemaConfiguracaoService {

    @Autowired
    private SistemaConfiguracaoRepository sistemaConfiguracaoRepository;

    public ResponseEmpresaDto cadastrarEmpresa(RequestEmpresaDto requestEmpresaDto) {
        SistemaConfiguracaoModel sistemaConfiguracao = new SistemaConfiguracaoModel();
        sistemaConfiguracao.setNomeEmpresa(requestEmpresaDto.nomeEmpresa());
        sistemaConfiguracao.setCnpj(requestEmpresaDto.cnpj());
        sistemaConfiguracao.setCepOrigem(requestEmpresaDto.cepOrigem());
        sistemaConfiguracao.setNumeroOrigem(requestEmpresaDto.numeroOrigem());
        sistemaConfiguracao.setCursoEntrega(requestEmpresaDto.custoEntrega());

        sistemaConfiguracao = sistemaConfiguracaoRepository.save(sistemaConfiguracao);

        ResponseEmpresaDto responseEmpresaDto = new ResponseEmpresaDto();
        responseEmpresaDto.setNomeEmpresa(sistemaConfiguracao.getNomeEmpresa());
        responseEmpresaDto.setCnpj(sistemaConfiguracao.getCnpj());
        responseEmpresaDto.setCepOrigem((sistemaConfiguracao.getCepOrigem()));
        responseEmpresaDto.setNumeroOrigem(sistemaConfiguracao.getNumeroOrigem());
        responseEmpresaDto.setCustoEntrega(sistemaConfiguracao.getCursoEntrega());
        return responseEmpresaDto;
    }

    public ResponseEmpresaDto buscarPorId(Long id) {
        SistemaConfiguracaoModel sistemaConfiguracao = sistemaConfiguracaoRepository.findById(id)
                .orElseThrow(() -> new EmpresaNaoEncontradaException(id));

        return new ResponseEmpresaDto(
                sistemaConfiguracao.getNomeEmpresa(),
                sistemaConfiguracao.getCnpj(),
                sistemaConfiguracao.getCepOrigem(),
                sistemaConfiguracao.getNumeroOrigem(),
                sistemaConfiguracao.getCursoEntrega()
        );
    }
}
