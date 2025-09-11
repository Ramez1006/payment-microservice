package com.example.paymentmicroservice.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cliente simplificado para ver o fluxo. Em produção substitua pelo SDK oficial
 * e implemente chamadas HTTP/SDK para criar pagamentos e consultar status.
 */
@Component
public class MercadoPagoClient {
    @Value("${mercadopago.access.token:dummy-token}")
    private String accessToken;

    public MercadoPagoClient() {}

    public MercadoPagoPaymentResponse createPayment(String externalReference, double amount) {
        // Simula uma criação de pagamento. Substitua com SDK.
        MercadoPagoPaymentResponse r = new MercadoPagoPaymentResponse();
        r.paymentId = "mp_" + System.currentTimeMillis();
        r.status = "in_process";
        r.paymentLink = "https://www.mercadopago.com/pay/" + r.paymentId;
        return r;
    }

    public MercadoPagoPaymentResponse getPayment(String paymentId) {
        MercadoPagoPaymentResponse r = new MercadoPagoPaymentResponse();
        r.paymentId = paymentId;
        r.status = "approved";
        r.paymentLink = "https://www.mercadopago.com/pay/" + paymentId;
        return r;
    }

    public static class MercadoPagoPaymentResponse {
        public String paymentId;
        public String status;
        public String paymentLink;
    }
}
