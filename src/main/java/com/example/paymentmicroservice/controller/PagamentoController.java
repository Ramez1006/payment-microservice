package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoRepository pagamentoRepository;

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPagamento(@PathVariable Long id) {
        return pagamentoRepository.findById(id).map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
