package com.example.paymentmicroservice.entity; // Define o pacote da classe
//Enum que representa os possíveis status de um pagamento
public enum PagamentoStatus {
    PROCESSANDO, // Pagamento está em processamento
    APROVADO,// Pagamento foi aprovado com sucesso
    REJEITADO //Pagamento foi rejeitado
}
