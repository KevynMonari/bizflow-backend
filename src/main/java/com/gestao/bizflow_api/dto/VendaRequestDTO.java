package com.gestao.bizflow_api.dto;

import com.gestao.bizflow_api.model.FormaPagamento;

import java.util.List;

public record VendaRequestDTO(
   FormaPagamento formaPagamento,
   List<ItemVendaRequestDTO> itens

) {}
