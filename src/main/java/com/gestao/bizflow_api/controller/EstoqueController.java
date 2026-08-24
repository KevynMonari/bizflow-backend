package com.gestao.bizflow_api.controller;

import com.gestao.bizflow_api.dto.MovimentacaoRequestDTO;
import com.gestao.bizflow_api.model.MovimentacaoEstoque;
import com.gestao.bizflow_api.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }

    @PostMapping("/movimentar")
    public ResponseEntity<MovimentacaoEstoque> movimentar (@RequestBody MovimentacaoRequestDTO request){
        return ResponseEntity.ok(estoqueService.registrarMovimentacao(request));
    }

    @GetMapping("/historico/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoque>> historico (@PathVariable Long produtoId){
        return ResponseEntity.ok(estoqueService.buscarHistoricoDoProduto(produtoId));
    }
}
