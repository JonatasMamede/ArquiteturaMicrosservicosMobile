package com.example.usuario.service.impl;

import com.example.usuario.domain.Usuario;
import com.example.usuario.repository.UsuarioRepository;
import com.example.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long, UsuarioRepository> implements UsuarioService {
    public UsuarioServiceImpl(UsuarioRepository repository){ super(repository);}


}
