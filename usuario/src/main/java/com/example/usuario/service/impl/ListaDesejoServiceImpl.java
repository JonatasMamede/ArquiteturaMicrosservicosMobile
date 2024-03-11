package com.example.usuario.service.impl;

import com.example.usuario.domain.ListaDesejo;
import com.example.usuario.repository.ListaDesejoRepository;
import com.example.usuario.service.ListaDesejoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Component
public class ListaDesejoServiceImpl extends GenericServiceImpl<ListaDesejo, Long, ListaDesejoRepository> implements ListaDesejoService {

    private final WebClient webClient;

    public ListaDesejoServiceImpl(ListaDesejoRepository repository, WebClient webClient){
        super(repository);
        this.webClient = webClient;
    }

    @Override
    public void save(ListaDesejo listaDesejo){

        this.webClient.get()
                .uri("produto/" + String.valueOf(listaDesejo.getProduto_id()))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){

                        repository.save(listaDesejo);
                        return response.toEntity(String.class);

                    } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return response.toEntity(String.class);
                    } else {
                        return  response.createError();
                    }
                }).block();
    }


}
