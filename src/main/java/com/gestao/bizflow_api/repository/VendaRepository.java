package com.gestao.bizflow_api.repository;

import com.gestao.bizflow_api.model.StatusVenda;
import com.gestao.bizflow_api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDataHoraBetweenAndStatus(LocalDateTime inicio, LocalDateTime fim, StatusVenda status);
}
