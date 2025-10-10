package com.example.paymentmicroservice.service;

import com.example.paymentmicroservice.dto.VendaRequest;
import com.example.paymentmicroservice.dto.VendaRequest.Item;
import com.example.paymentmicroservice.dto.VendaResponse;
import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.entity.VendaStatus;
import com.example.paymentmicroservice.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock VendaRepository vendaRepository;
    @Mock PagamentoService pagamentoService; // colaboração externa

    @InjectMocks VendaService vendaService;

    @Test
    void criarVenda_deveCalcularTotalPersistirEAcionarPagamento() {
        // Arrange - request com dois itens
        Item i1 = new Item();
        i1.produtoId = 1L;
        i1.quantidade = 2;
        i1.precoUnitario = new BigDecimal("10.00");

        Item i2 = new Item();
        i2.produtoId = 2L;
        i2.quantidade = 1;
        i2.precoUnitario = new BigDecimal("5.50");

        VendaRequest req = new VendaRequest();
        req.clienteId = 77L;
        req.itens = java.util.List.of(i1, i2);

        when(vendaRepository.save(any(Venda.class)))
                .thenAnswer(inv -> {
                    Venda v = inv.getArgument(0);
                    v.setId(123L);
                    return v;
                });

        // Act
        VendaResponse resp = vendaService.criarVenda(req);

        // Assert: repositorio recebeu venda com total 25.50 e status inicial
        ArgumentCaptor<Venda> cap = ArgumentCaptor.forClass(Venda.class);
        verify(vendaRepository, atLeastOnce()).save(cap.capture());
        Venda salvo = cap.getValue();
        assertThat(salvo.getClienteId()).isEqualTo(77L);
        assertThat(salvo.getValorTotal()).isEqualByComparingTo("25.50");
        assertThat(salvo.getStatus()).isIn(VendaStatus.PENDENTE, VendaStatus.PROCESSANDO);

        // Serviço de pagamento foi acionado
        verify(pagamentoService).criarPagamentoParaVenda(salvo);

        // Response básico montado
        assertThat(resp).isNotNull();
        assertThat(resp.id).isEqualTo(123L);
        assertThat(resp.valorTotal).isEqualByComparingTo("25.50");
        assertThat(resp.status).isIn("PENDENTE", "PROCESSANDO");
    }

    @Test
    void atualizarStatusVenda_deveSalvarNovoStatus() {
        Venda v = new Venda();
        v.setId(9L);
        v.setStatus(VendaStatus.PENDENTE);

        when(vendaRepository.findById(9L)).thenReturn(java.util.Optional.of(v));

        vendaService.atualizarStatusVenda(9L, VendaStatus.APROVADO);

        assertThat(v.getStatus()).isEqualTo(VendaStatus.APROVADO);
        verify(vendaRepository).save(v);
    }
}
