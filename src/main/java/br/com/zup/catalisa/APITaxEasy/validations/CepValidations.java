package br.com.zup.catalisa.APITaxEasy.validations;

import br.com.zup.catalisa.APITaxEasy.exception.CepFormatException;
import br.com.zup.catalisa.APITaxEasy.exception.CepNullException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CepValidations {
    public void validaCep(String cep) throws CepNullException {
        if (Objects.isNull(cep) || cep.isEmpty() || cep.isBlank()) throw new CepNullException("O cep informado não pode ser nulo ou vazio");
        if (cep.length() > 9) throw new CepFormatException("CEP com números a mais");
        if (cep.length() < 8) throw new CepFormatException("CEP faltando números");
    }
    public String removedorDeMascaraCep(String cep){
        try {
            validaCep(cep);
            return cep;
        } catch (CepFormatException e){
            return cep.replace("-", "");
        }
    }
}
