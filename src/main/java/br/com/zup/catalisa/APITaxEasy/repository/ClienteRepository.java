package br.com.zup.catalisa.APITaxEasy.repository;

import br.com.zup.catalisa.APITaxEasy.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
}
