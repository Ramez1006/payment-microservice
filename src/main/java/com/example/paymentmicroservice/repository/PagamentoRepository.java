package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
