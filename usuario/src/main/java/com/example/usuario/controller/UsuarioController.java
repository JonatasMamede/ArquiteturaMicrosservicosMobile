package com.example.usuario.controller;

import com.example.usuario.domain.Usuario;
import com.example.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController extends GenericController<Usuario> {
    public UsuarioController(UsuarioService service){ super(service);}
}
