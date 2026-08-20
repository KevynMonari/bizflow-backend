package com.gestao.bizflow_api.controller;

import com.gestao.bizflow_api.dto.ProdutoResponseDTO;
import com.gestao.bizflow_api.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gestao.bizflow_api.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        Produto novo = service.salvar(produto);
        return ResponseEntity.ok(novo);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody Produto produto){
        return ResponseEntity.ok(service.atualizar(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
