package com.example.carrinho.rapository;

import com.example.carrinho.domain.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {
}
