package com.gestao.bizflow_api.repository;

import com.gestao.bizflow_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
