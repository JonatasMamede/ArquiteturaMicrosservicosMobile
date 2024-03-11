package com.example.usuario.controller;

import com.example.usuario.domain.ListaDesejo;
import com.example.usuario.service.ListaDesejoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/listaDesejo")
public class ListaDesejoController extends GenericController<ListaDesejo> {
    public ListaDesejoController (ListaDesejoService service){ super(service);}
}
