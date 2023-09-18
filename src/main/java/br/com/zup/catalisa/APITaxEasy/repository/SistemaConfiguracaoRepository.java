package br.com.zup.catalisa.APITaxEasy.repository;

import br.com.zup.catalisa.APITaxEasy.model.SistemaConfiguracaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaConfiguracaoRepository extends JpaRepository<SistemaConfiguracaoModel, Long> {
}
