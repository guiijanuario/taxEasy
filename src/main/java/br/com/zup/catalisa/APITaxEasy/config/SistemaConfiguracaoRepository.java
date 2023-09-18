package br.com.zup.catalisa.APITaxEasy.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaConfiguracaoRepository extends JpaRepository<SistemaConfiguracaoModel, Long> {
}
