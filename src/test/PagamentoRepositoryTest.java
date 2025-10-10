package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.entity.PagamentoStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PagamentoRepositoryTest {

    @Autowired
    PagamentoRepository pagamentoRepository;

    private Pagamento novoPagamento() {
        Pagamento p = new Pagamento();
        p.setVendaId(100L);
        p.setMetodo("PIX");
        p.setStatus(PagamentoStatus.PROCESSANDO);
        p.setValor(new BigDecimal("59.90"));
        p.setCriadoEm(OffsetDateTime.parse("2024-01-10T10:00:00Z"));
        p.setAtualizadoEm(OffsetDateTime.parse("2024-01-10T10:00:00Z"));
        return p;
    }

    @Test
    void save_findById_update_delete_flow() {
        // save
        Pagamento salvo = pagamentoRepository.save(novoPagamento());
        assertThat(salvo.getId()).isNotNull();

        // findById
        Optional<Pagamento> found = pagamentoRepository.findById(salvo.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStatus()).isEqualTo(PagamentoStatus.PROCESSANDO);

        // update
        found.get().setStatus(PagamentoStatus.APROVADO);
        pagamentoRepository.save(found.get());

        Pagamento updated = pagamentoRepository.findById(salvo.getId()).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(PagamentoStatus.APROVADO);

        // findAll
        assertThat(pagamentoRepository.findAll()).isNotEmpty();

        // delete
        pagamentoRepository.deleteById(salvo.getId());
        assertThat(pagamentoRepository.findById(salvo.getId())).isEmpty();
    }
}
