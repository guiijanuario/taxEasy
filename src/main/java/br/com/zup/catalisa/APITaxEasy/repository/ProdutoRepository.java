package br.com.zup.catalisa.APITaxEasy.repository;

import br.com.zup.catalisa.APITaxEasy.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
}
