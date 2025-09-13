package com.example.paymentmicroservice.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cliente simplificado para ver o fluxo de pagamento com o Mercado Pago.
 * ⚠️ Em produção: substitua pelo SDK oficial e implemente chamadas HTTP/SDK
 * reais para criar pagamentos e consultar o status.
 */
@Component
public class MercadoPagoClient {

    // Token de acesso do Mercado Pago, injetado via application.properties.
    // Caso não seja configurado, assume o valor "dummy-token".
    @Value("${mercadopago.access.token:dummy-token}")
    private String accessToken;

    // Construtor padrão vazio
    public MercadoPagoClient() {}

    /**
     * Simula a criação de um pagamento no Mercado Pago.
     * Em produção, este método deve chamar o SDK/HTTP do Mercado Pago.
     *
     * @param externalReference referência externa para identificar o pagamento
     * @param amount valor do pagamento
     * @return objeto de resposta com ID, status inicial e link de pagamento
     */
    public MercadoPagoPaymentResponse createPayment(String externalReference, double amount) {
        // Cria uma resposta fake de pagamento
        MercadoPagoPaymentResponse r = new MercadoPagoPaymentResponse();
        r.paymentId = "mp_" + System.currentTimeMillis(); // Gera ID único baseado no timestamp
        r.status = "in_process"; // Status inicial simulado
        r.paymentLink = "https://www.mercadopago.com/pay/" + r.paymentId; // Link de pagamento fake
        return r;
    }

    /**
     * Simula a consulta de um pagamento no Mercado Pago.
     * Em produção, deve buscar no SDK/HTTP real.
     *
     * @param paymentId identificador do pagamento
     * @return objeto de resposta com dados simulados do pagamento
     */
    public MercadoPagoPaymentResponse getPayment(String paymentId) {
        MercadoPagoPaymentResponse r = new MercadoPagoPaymentResponse();
        r.paymentId = paymentId; // Retorna o mesmo ID solicitado
        r.status = "approved";   // Simula que o pagamento foi aprovado
        r.paymentLink = "https://www.mercadopago.com/pay/" + paymentId; // Link de pagamento fake
        return r;
    }

    /**
     * Classe interna que representa a resposta de um pagamento no Mercado Pago.
     */
    public static class MercadoPagoPaymentResponse {
        public String paymentId;   // Identificador único do pagamento
        public String status;      // Status atual do pagamento (ex.: in_process, approved, rejected)
        public String paymentLink; // Link onde o cliente pode efetuar o pagamento
    }
}
