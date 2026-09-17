package com.gestao.bizflow_api.dto;

import java.math.BigDecimal;

public record DashboardResponseDTO(
        BigDecimal faturamentoTotal,
        BigDecimal custoTotal,
        BigDecimal lucroLiquido,
        Long quantidadeVendas,
        BigDecimal margemLucroPercentual
) {}
