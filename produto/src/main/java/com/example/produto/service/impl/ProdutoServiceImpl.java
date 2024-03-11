package com.example.produto.service.impl;

import com.example.produto.domain.Produto;
import com.example.produto.repository.ProdutoRepository;
import com.example.produto.service.ProdutoService;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl extends GenericServiceImpl<Produto, Long, ProdutoRepository> implements ProdutoService {
    public ProdutoServiceImpl(ProdutoRepository repository){ super(repository);}
}
