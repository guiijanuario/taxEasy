package br.com.zup.catalisa.APITaxEasy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(info = @Info(title = "API - Busca Cep (Via Cep)", version = "1", description = "API desenvolvida para buscar endereço pelo CEP"))
@SpringBootApplication
@EnableCaching
public class ApiTaxEasyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiTaxEasyApplication.class, args);
	}

}
