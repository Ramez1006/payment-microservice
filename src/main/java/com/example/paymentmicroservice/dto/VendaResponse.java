package com.example.paymentmicroservice.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class VendaResponse {
    public Long id;
    public OffsetDateTime dataCriacao;
    public BigDecimal valorTotal;
    public String status;
}
