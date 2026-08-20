package com.gestao.bizflow_api.service;

import com.gestao.bizflow_api.dto.ProdutoResponseDTO;
import com.gestao.bizflow_api.model.Produto;
import org.springframework.stereotype.Service;
import com.gestao.bizflow_api.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository){
        this.repository = repository;
    }

    public Produto salvar(Produto produto){
        return repository.save(produto);
    }

    public List<ProdutoResponseDTO> listarTodos(){
        return repository.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        return converterParaDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, Produto dadosAtualizados){
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setTipo(dadosAtualizados.getTipo());
        produtoExistente.setPrecoCusto(dadosAtualizados.getPrecoCusto());
        produtoExistente.setPrecoVenda(dadosAtualizados.getPrecoVenda());
        produtoExistente.setEstoqueAtual(dadosAtualizados.getEstoqueAtual());

        Produto produtoSalvo = repository.save(produtoExistente);
        return converterParaDTO(produtoSalvo);
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto){
        BigDecimal custo = produto.getPrecoCusto()!= null ? produto.getPrecoCusto() : BigDecimal.ZERO;
        BigDecimal venda = produto.getPrecoVenda() != null ? produto.getPrecoVenda() : BigDecimal.ZERO;
        String nomeCat = (produto.getCategoria() != null) ? produto.getCategoria().getNome() : "Sem Categoria";

        BigDecimal lucro = venda.subtract(custo);

        BigDecimal margem = BigDecimal.ZERO;
        if (venda.compareTo(BigDecimal.ZERO) > 0){
            margem = lucro.divide(venda, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getTipo(),
                custo,
                venda,
                produto.getEstoqueAtual(),
                lucro,
                margem,
                nomeCat
        );
    }
}
