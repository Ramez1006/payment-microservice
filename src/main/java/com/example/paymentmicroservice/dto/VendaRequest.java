package com.example.paymentmicroservice.dto;

import java.math.BigDecimal;
import java.util.List;

public class VendaRequest {
    public static class Item {
        public Long produtoId;
        public Integer quantidade;
        public BigDecimal precoUnitario;
    }
    public Long clienteId;
    public List<Item> itens;
}
