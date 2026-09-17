package com.gestao.bizflow_api.service;

import com.gestao.bizflow_api.dto.MovimentacaoRequestDTO;
import com.gestao.bizflow_api.exception.EntidadeNaoEncontradaException;
import com.gestao.bizflow_api.exception.EstoqueInsuficienteException;
import com.gestao.bizflow_api.model.MovimentacaoEstoque;
import com.gestao.bizflow_api.model.Produto;
import com.gestao.bizflow_api.repository.MovimentacaoEstoqueRepository;
import com.gestao.bizflow_api.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public EstoqueService(ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoRepository){
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoRequestDTO request){
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado com o ID " + request.produtoId()));

        Double quantidadeAtual = produto.getEstoqueAtual() != null ? produto.getEstoqueAtual() : 0.0;

        switch (request.tipo()){
            case ENTRADA -> produto.setEstoqueAtual(quantidadeAtual + request.quantidade());
            case SAIDA, PERDA -> {
                if (quantidadeAtual < request.quantidade()) {
                    throw new EstoqueInsuficienteException("Estoque insuficiente! Estoque atual: " + quantidadeAtual);
                }
                produto.setEstoqueAtual(quantidadeAtual - request.quantidade());
            }
            case AJUSTE -> produto.setEstoqueAtual(request.quantidade());
        }

        produtoRepository.save(produto);

        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setProduto(produto);
        mov.setQuantidade(request.quantidade());
        mov.setTipo(request.tipo());
        mov.setMotivo(request.motivo());

        return movimentacaoRepository.save(mov);
    }

    public List<MovimentacaoEstoque> buscarHistoricoDoProduto(Long produtoId){
        return movimentacaoRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId);
    }
}
