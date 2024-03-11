package com.example.produto.controller;

import com.example.produto.domain.Produto;
import com.example.produto.service.ProdutoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produto")
public class ProdutoController extends GenericController<Produto>{
    public ProdutoController(ProdutoService service){super(service);}
}
