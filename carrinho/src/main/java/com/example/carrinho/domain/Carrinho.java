package com.example.carrinho.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "`carrinho`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer status = 1;

    @Column(nullable = false, updatable = false)
    private Long user_id;

    @Column(nullable = false, updatable = false)
    private Double valor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarrinhoItem> carrinhoItems = new HashSet<>();

    @PrePersist
    protected void onCreated(){
        createdAt = LocalDateTime.now();
    }
}
