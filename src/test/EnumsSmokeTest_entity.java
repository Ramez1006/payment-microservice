package com.example.paymentmicroservice.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EnumsSmokeTest {
    @Test
    void pagamentoStatusValues() {
        assertThat(PagamentoStatus.values()).contains(PagamentoStatus.PROCESSANDO, PagamentoStatus.APROVADO, PagamentoStatus.REJEITADO);
    }
    @Test
    void vendaStatusValues() {
        assertThat(VendaStatus.values()).contains(VendaStatus.PENDENTE, VendaStatus.APROVADO, VendaStatus.RECUSADO);
    }
}
