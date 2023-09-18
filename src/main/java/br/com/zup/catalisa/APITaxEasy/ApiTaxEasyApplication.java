package br.com.zup.catalisa.APITaxEasy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@OpenAPIDefinition(info = @Info(title = "API - Sistema de Vendas de Produtos", version = "1", description = "API Desenvolvida para calcular vendas de produtos e buscar cep"))
@SpringBootApplication
@EnableCaching
public class ApiTaxEasyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiTaxEasyApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("4321"));
	}

}
