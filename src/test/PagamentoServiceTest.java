package com.example.paymentmicroservice.service;

import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.entity.PagamentoStatus;
import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.integration.MercadoPagoClient;
import com.example.paymentmicroservice.integration.MercadoPagoClient.MercadoPagoPaymentResponse;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import com.example.paymentmicroservice.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock PagamentoRepository pagamentoRepository;
    @Mock VendaRepository vendaRepository;
    @Mock MercadoPagoClient mercadoPagoClient;

    @InjectMocks PagamentoService pagamentoService;

    @BeforeEach
    void setup() {
    }

    @Test
    void criarPagamentoParaVenda_devePersistirPagamentoEChamarGateway() {
        // Arrange
        Venda venda = new Venda();
        venda.setId(10L);
        venda.setValorTotal(new BigDecimal("25.50"));

        MercadoPagoPaymentResponse mpResp = new MercadoPagoPaymentResponse();
        mpResp.paymentId = "pay_123";
        mpResp.status = "approved";
        mpResp.paymentLink = "https://mp/pay/pay_123";

        when(mercadoPagoClient.criarPagamento(any(BigDecimal.class), anyString()))
                .thenReturn(mpResp);

        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(inv -> {
                    Pagamento p = inv.getArgument(0);
                    p.setId(1L);
                    return p;
                });

        // Act
        Pagamento criado = pagamentoService.criarPagamentoParaVenda(venda);

        // Assert: persistiu com campos básicos e status inicial
        assertThat(criado.getId()).isEqualTo(1L);
        ArgumentCaptor<Pagamento> cap = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoRepository).save(cap.capture());
        Pagamento salvo = cap.getValue();
        assertThat(salvo.getVendaId()).isEqualTo(10L);
        assertThat(salvo.getValor()).isEqualByComparingTo("25.50");
        assertThat(salvo.getStatus()).isIn(PagamentoStatus.PROCESSANDO, PagamentoStatus.APROVADO, PagamentoStatus.REJEITADO);
        verify(mercadoPagoClient).criarPagamento(new BigDecimal("25.50"), "VENDA-10");
    }

    @Test
    void atualizarStatus_quandoExiste_devePersistirNovoStatus() {
        Pagamento p = new Pagamento();
        p.setId(5L);
        p.setStatus(PagamentoStatus.PROCESSANDO);
        when(pagamentoRepository.findById(5L)).thenReturn(Optional.of(p));

        pagamentoService.atualizarStatus(5L, PagamentoStatus.APROVADO);

        assertThat(p.getStatus()).isEqualTo(PagamentoStatus.APROVADO);
        verify(pagamentoRepository).save(p);
    }
}
