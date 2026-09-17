package com.gestao.bizflow_api.dto;

public record ItemVendaRequestDTO(
        Long produtoId,
        Double quantidade
) { }
