package com.gestao.bizflow_api.service;

import com.gestao.bizflow_api.dto.ItemVendaRequestDTO;
import com.gestao.bizflow_api.dto.MovimentacaoRequestDTO;
import com.gestao.bizflow_api.dto.VendaRequestDTO;
import com.gestao.bizflow_api.model.ItemVenda;
import com.gestao.bizflow_api.model.Produto;
import com.gestao.bizflow_api.model.TipoMovimentacao;
import com.gestao.bizflow_api.model.Venda;
import com.gestao.bizflow_api.repository.ProdutoRepository;
import com.gestao.bizflow_api.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, EstoqueService estoqueService){
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public Venda realizarVenda(VendaRequestDTO request){
        Venda venda = new Venda();
        venda.setFormaPagamento(request.formaPagamento());

        BigDecimal totalVenda = BigDecimal.ZERO;

        for (ItemVendaRequestDTO itemDTO : request.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDTO.produtoId()));

            estoqueService.registrarMovimentacao(new MovimentacaoRequestDTO(
                    produto.getId(),
                    itemDTO.quantidade(),
                    TipoMovimentacao.SAIDA,
                    "Saída por Venda"
            ));

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPrecoVenda());

            BigDecimal subtotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(itemDTO.quantidade()));
            item.setSubtotal(subtotal);

            venda.getItens().add(item);
            totalVenda = totalVenda.add(subtotal);

        }

        venda.setValorTotal(totalVenda);
        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodas (){
        return vendaRepository.findAll();
    }
}
