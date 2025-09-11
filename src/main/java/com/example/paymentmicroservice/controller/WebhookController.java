package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.dto.WebhookMercadoPagoDTO;
import com.example.paymentmicroservice.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/mercadopago")
public class WebhookController {
    private final PagamentoService pagamentoService;

    public WebhookController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<?> receiveWebhook(@RequestBody WebhookMercadoPagoDTO dto) {
        // Em produção valide a assinatura e revalide o pagamento consultando a API do MP.
        if (dto == null || dto.id == null) {
            return ResponseEntity.badRequest().build();
        }
        pagamentoService.sincronizarStatus(dto.id);
        return ResponseEntity.ok().build();
    }
}
