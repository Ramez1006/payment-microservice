package com.example.paymentmicroservice.entity;// Define o pacote onde esta classe/enum está localizada

public enum VendaStatus {// Declaração de um enum (tipo especial do Java usado para representar constantes fixas)
    PENDENTE,// Constante que representa uma venda que ainda não foi tratada
    PROCESSANDO,// Constante que representa uma venda que está em processamento
    APROVADO,    // Constante que representa uma venda que foi aprovada
    RECUSADO// Constante que representa uma venda que foi recusada
}
