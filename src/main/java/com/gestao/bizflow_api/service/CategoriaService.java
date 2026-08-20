package com.gestao.bizflow_api.service;

import com.gestao.bizflow_api.model.Categoria;
import com.gestao.bizflow_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository){
        this.repository = repository;
    }

    public Categoria salvar(Categoria categoria){
        return repository.save(categoria);
    }

    public List<Categoria> listarTodas(){
        return repository.findAll();
    }

    public Categoria buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }
}
