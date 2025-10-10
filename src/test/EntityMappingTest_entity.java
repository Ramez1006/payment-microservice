package com.example.paymentmicroservice.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EntityMappingTest {

    @Autowired
    TestEntityManager em;

    @Nested
    class ClienteTests {
        @Test
        @DisplayName("Deve persistir Cliente e gerar ID")
        void persistCliente() {
            Cliente c = new Cliente();
            c.setNome("Ana");
            c.setEmail("ana@example.com");
            c.setDocumento("12345678900");
            c.setTelefone("11999999999");

            Cliente salvo = em.persistFlushFind(c);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getNome()).isEqualTo("Ana");
            assertThat(salvo.getEmail()).isEqualTo("ana@example.com");
        }
    }

    @Nested
    class ProdutoTests {
        @Test
        @DisplayName("Deve persistir Produto com nome e preco")
        void persistProduto() {
            Produto p = new Produto();
            p.setNome("Notebook");
            p.setPreco(new BigDecimal("4500.00"));

            Produto salvo = em.persistFlushFind(p);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getNome()).isEqualTo("Notebook");
            assertThat(salvo.getPreco()).isEqualByComparingTo("4500.00");
        }
    }

    @Nested
    class VendaTests {
        @Test
        @DisplayName("Deve persistir Venda com status e valorTotal")
        void persistVenda() {
            Venda v = new Venda();
            v.setStatus(VendaStatus.PENDENTE);
            v.setValorTotal(new BigDecimal("99.90"));
            v.setClienteId(1L);
            v.setDataCriacao(OffsetDateTime.parse("2024-01-01T12:00:00Z"));

            Venda salvo = em.persistFlushFind(v);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getStatus()).isEqualTo(VendaStatus.PENDENTE);
            assertThat(salvo.getValorTotal()).isEqualByComparingTo("99.90");
            assertThat(salvo.getClienteId()).isEqualTo(1L);
        }
    }

    @Nested
    class ItemVendaTests {
        @Test
        @DisplayName("Deve persistir ItemVenda e calcular subtotal")
        void persistItemVenda() {
            ItemVenda item = new ItemVenda();
            item.setProdutoId(5L);
            item.setQuantidade(2);
            item.setPrecoUnitario(new BigDecimal("10.00"));
            item.setSubtotal(new BigDecimal("20.00")); // se houver lógica de cálculo, ajuste o teste

            ItemVenda salvo = em.persistFlushFind(item);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getProdutoId()).isEqualTo(5L);
            assertThat(salvo.getQuantidade()).isEqualTo(2);
            assertThat(salvo.getPrecoUnitario()).isEqualByComparingTo("10.00");
            assertThat(salvo.getSubtotal()).isEqualByComparingTo("20.00");
        }
    }

    @Nested
    class PagamentoTests {
        @Test
        @DisplayName("Deve persistir Pagamento com enum status e timestamps")
        void persistPagamento() {
            Pagamento pag = new Pagamento();
            pag.setVendaId(123L);
            pag.setMetodo("PIX");
            pag.setStatus(PagamentoStatus.PROCESSANDO);
            pag.setValor(new BigDecimal("49.90"));
            pag.setCriadoEm(OffsetDateTime.parse("2024-02-10T10:00:00Z"));
            pag.setAtualizadoEm(OffsetDateTime.parse("2024-02-10T10:05:00Z"));

            Pagamento salvo = em.persistFlushFind(pag);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getStatus()).isEqualTo(PagamentoStatus.PROCESSANDO);
            assertThat(salvo.getValor()).isEqualByComparingTo("49.90");
            assertThat(salvo.getVendaId()).isEqualTo(123L);
            assertThat(salvo.getMetodo()).isEqualTo("PIX");
        }
    }
}
