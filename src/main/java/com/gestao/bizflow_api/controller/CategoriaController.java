package com.gestao.bizflow_api.controller;

import com.gestao.bizflow_api.model.Categoria;
import com.gestao.bizflow_api.service.CategoriaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria){
        return ResponseEntity.ok(service.salvar(categoria));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        return ResponseEntity.ok(service.listarTodas());
    }
}
