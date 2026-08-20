package com.gestao.bizflow_api.dto;

import com.gestao.bizflow_api.model.TipoItem;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        TipoItem tipo,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Double estoqueAtual,
        BigDecimal lucroBruto,
        BigDecimal margemLucroPercentual,
        String nomeCategoria
) {}
