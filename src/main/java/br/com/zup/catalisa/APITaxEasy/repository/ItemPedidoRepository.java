package br.com.zup.catalisa.APITaxEasy.repository;

import br.com.zup.catalisa.APITaxEasy.model.ItemPedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoModel, Long> {
}
