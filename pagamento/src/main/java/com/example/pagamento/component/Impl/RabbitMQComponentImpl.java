package com.example.pagamento.component.Impl;

import com.example.pagamento.component.RabbitMQComponent;
import com.example.pagamento.domain.Pagamento;
import com.example.pagamento.service.Impl.EmailServiceImpl;
import com.example.pagamento.service.Impl.PagamentoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.swing.*;
import java.util.Map;

@Component
public class RabbitMQComponentImpl implements RabbitMQComponent {
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    private final WebClient webClient;

    public RabbitMQComponentImpl(WebClient webClient){
        this.webClient = webClient;
    }

    public Map<String, Object> convertToObject(String jsonS){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(jsonS, Map.class);
            return map;
        }catch (JsonProcessingException e){
            return null;
        }
    }

    @RabbitListener(queues = "carrinho_notification")
    public void handleMessage(String message){

        Map<String, Object> obj = convertToObject(message);

        int user_id = (int) obj.get("user_id");

        String response = this.webClient.get()
                .uri("user/" + String.valueOf(user_id))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, Object> user = convertToObject(message);

        String content = emailServiceImpl.constructOrderContent((String) user.get("username"));

        emailServiceImpl.sendEmail(content, (String) user.get("email"), "Compra realizada com sucesso!!");
    }

    @RabbitListener(queues = "carrinho_notification")
    public void handleCar(String carrinho){

        Map<String, Object> obj = convertToObject(carrinho);

        int carrinho_id = (int) obj.get("id");

        this.webClient.get()
                .uri("carrinho/" + String.valueOf(carrinho_id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
