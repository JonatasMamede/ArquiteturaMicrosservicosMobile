package com.example.pagamento.controller;

import com.example.pagamento.domain.Pagamento;
import com.example.pagamento.service.PagamentoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pagamento")
public class PagamentoController extends GenericController<Pagamento>{

    public PagamentoController(PagamentoService service){ super(service);}
}
