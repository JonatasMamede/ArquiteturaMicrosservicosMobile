package com.example.carrinho.controller;

import com.example.carrinho.domain.Carrinho;
import com.example.carrinho.service.CarrinhoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/carrinho")
public class CarrinhoController extends GenericController<Carrinho>{
    public CarrinhoController(CarrinhoService service){ super(service);}
}