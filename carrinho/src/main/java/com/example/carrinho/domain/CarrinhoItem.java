package com.example.carrinho.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "`carrinho_item`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long product_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="carrinho_id")
    @JsonBackReference
    private Carrinho carrinho;
}
