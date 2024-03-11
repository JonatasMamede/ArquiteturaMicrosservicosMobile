package com.example.carrinho.service.impl;

import com.example.carrinho.domain.Carrinho;
import com.example.carrinho.domain.CarrinhoItem;
import com.example.carrinho.rapository.CarrinhoItemRepository;
import com.example.carrinho.rapository.CarrinhoRepository;
import com.example.carrinho.service.CarrinhoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Component
public class CarrinhoServiceImpl extends GenericServiceImpl<Carrinho, Long, CarrinhoRepository> implements CarrinhoService {

    private final WebClient webClient;

    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingkey;

    private final AmqpTemplate rabbitTemplate;

    public CarrinhoServiceImpl(CarrinhoRepository repository, WebClient webClient, AmqpTemplate rabbitTemplate){
        super(repository);
        this.webClient = webClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void save(Carrinho carrinho){

        this.webClient.get()
                .uri("carrinho/" + String.valueOf(carrinho.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){

                        Carrinho carr = repository.save(carrinho);

                        for (CarrinhoItem item: carr.getCarrinhoItems()){
                            CarrinhoItem carrinhoItem = new CarrinhoItem();
                            carrinhoItem.setCarrinho(carr);
                            carrinhoItem.setProduct_id(item.getProduct_id());

                            carrinhoItemRepository.save(carrinhoItem);
                        }

                        this.sendParaPagamento(carr);
                        return response.toEntity(String.class);

                    } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return response.toEntity(String.class);
                    } else {
                        return  response.createError();
                    }
                }).block();
    }

    public void sendParaPagamento(Carrinho carrinho){
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JavaTimeModule());

            String json = mapper.writeValueAsString(carrinho);

            rabbitTemplate.convertAndSend(exchange, routingkey, json);

        }catch (JsonProcessingException e){
        }

    }
}
