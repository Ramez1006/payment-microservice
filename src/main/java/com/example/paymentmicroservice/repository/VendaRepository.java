package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
