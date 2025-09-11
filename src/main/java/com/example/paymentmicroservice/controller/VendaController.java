package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.dto.VendaRequest;
import com.example.paymentmicroservice.dto.VendaResponse;
import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import com.example.paymentmicroservice.repository.VendaRepository;
import com.example.paymentmicroservice.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService vendaService;
    private final VendaRepository vendaRepository;
    private final PagamentoRepository pagamentoRepository;

    public VendaController(VendaService vendaService, VendaRepository vendaRepository, PagamentoRepository pagamentoRepository) {
        this.vendaService = vendaService;
        this.vendaRepository = vendaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @PostMapping
    public ResponseEntity<VendaResponse> criarVenda(@RequestBody VendaRequest request) {
        VendaResponse resp = vendaService.criarVenda(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVenda(@PathVariable Long id) {
        return vendaRepository.findById(id).map(v -> ResponseEntity.ok(v))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
