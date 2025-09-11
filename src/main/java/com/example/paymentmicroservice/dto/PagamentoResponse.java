package com.example.paymentmicroservice.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PagamentoResponse {
    public Long id;
    public Long vendaId;
    public String status;
    public BigDecimal valor;
    public String linkPagamento;
    public OffsetDateTime criadoEm;
}
