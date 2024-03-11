package com.example.pagamento.service.Impl;

import com.example.pagamento.domain.Pagamento;
import com.example.pagamento.repository.PagamentoRepository;
import com.example.pagamento.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagamentoServiceImpl extends GenericServiceImpl<Pagamento, Long, PagamentoRepository> implements PagamentoService {

    private final WebClient webClient;

    public PagamentoServiceImpl(PagamentoRepository repository, WebClient webClient){
        super(repository);
        this.webClient = webClient;
    }

    @Override
    public void save(Pagamento pagamento){

        this.webClient.get()
                .uri("carrinho/" + String.valueOf(pagamento.getUser_id()))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                   if(response.statusCode().equals(HttpStatus.OK)){

                       repository.save(pagamento);
                       return response.toEntity(String.class);

                   } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                       System.out.println("Carrinho inesistente!");
                       return response.toEntity(String.class);
                   } else {
                       return  response.createError();
                   }
               }).block();
    }

}
