package com.gestao.bizflow_api.controller;

import com.gestao.bizflow_api.dto.VendaRequestDTO;
import com.gestao.bizflow_api.model.Venda;
import com.gestao.bizflow_api.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService){
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequestDTO request){
        return ResponseEntity.ok(vendaService.realizarVenda(request));
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listar() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }
}
