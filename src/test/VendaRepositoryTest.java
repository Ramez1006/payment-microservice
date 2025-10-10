package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.entity.VendaStatus;
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
class VendaRepositoryTest {

    @Autowired
    VendaRepository vendaRepository;

    private Venda novaVenda() {
        Venda v = new Venda();
        v.setStatus(VendaStatus.PENDENTE);
        v.setValorTotal(new BigDecimal("120.00"));
        v.setClienteId(7L);
        v.setDataCriacao(OffsetDateTime.parse("2024-06-01T08:30:00Z"));
        return v;
    }

    @Test
    void save_findById_update_delete_flow() {
        // save
        Venda salva = vendaRepository.save(novaVenda());
        assertThat(salva.getId()).isNotNull();

        // findById
        Optional<Venda> found = vendaRepository.findById(salva.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStatus()).isEqualTo(VendaStatus.PENDENTE);

        // update
        found.get().setStatus(VendaStatus.APROVADO);
        vendaRepository.save(found.get());

        Venda updated = vendaRepository.findById(salva.getId()).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(VendaStatus.APROVADO);

        // findAll
        assertThat(vendaRepository.findAll()).isNotEmpty();

        // delete
        vendaRepository.deleteById(salva.getId());
        assertThat(vendaRepository.findById(salva.getId())).isEmpty();
    }
}
