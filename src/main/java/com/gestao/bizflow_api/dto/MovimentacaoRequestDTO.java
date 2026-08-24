package com.gestao.bizflow_api.dto;

import com.gestao.bizflow_api.model.TipoMovimentacao;

public record MovimentacaoRequestDTO(
        Long produtoId,
        Double quantidade,
        TipoMovimentacao tipo,
        String motivo
) {}
