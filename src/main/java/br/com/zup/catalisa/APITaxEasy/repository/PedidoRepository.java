package br.com.zup.catalisa.APITaxEasy.repository;

import br.com.zup.catalisa.APITaxEasy.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
}
