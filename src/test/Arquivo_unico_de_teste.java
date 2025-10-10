package com.example.paymentmicroservice.test;

import com.example.paymentmicroservice.controller.PagamentoController;
import com.example.paymentmicroservice.controller.VendaController;
import com.example.paymentmicroservice.controller.WebhookController;

import com.example.paymentmicroservice.dto.PagamentoResponse;
import com.example.paymentmicroservice.dto.VendaRequest;
import com.example.paymentmicroservice.dto.VendaResponse;
import com.example.paymentmicroservice.dto.WebhookMercadoPagoDTO;

import com.example.paymentmicroservice.entity.Cliente;
import com.example.paymentmicroservice.entity.ItemVenda;
import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.entity.PagamentoStatus;
import com.example.paymentmicroservice.entity.Produto;
import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.entity.VendaStatus;

import com.example.paymentmicroservice.integration.MercadoPagoClient;
import com.example.paymentmicroservice.integration.MercadoPagoClient.MercadoPagoPaymentResponse;

import com.example.paymentmicroservice.repository.PagamentoRepository;
import com.example.paymentmicroservice.repository.VendaRepository;

import com.example.paymentmicroservice.service.PagamentoService;
import com.example.paymentmicroservice.service.VendaService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Arquivo único contendo TODAS as classes de teste como classes internas estáticas.
 * Vantagem: um .java só, várias classes *Test detectadas pelo Surefire.
 */
public class Arquivo_unico_de_teste {

    // ======================= CONTROLLERS (WebMvcTest) =======================
    @WebMvcTest(controllers = PagamentoController.class)
    public static class PagamentoControllerTest {
        @Autowired MockMvc mvc;
        @MockBean PagamentoRepository pagamentoRepository;

        @Test
        void getPagamento_quandoExiste_retorna200() throws Exception {
            when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(new Pagamento()));
            mvc.perform(get("/pagamentos/1").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        }

        @Test
        void getPagamento_quandoNaoExiste_retorna404() throws Exception {
            when(pagamentoRepository.findById(999L)).thenReturn(Optional.empty());
            mvc.perform(get("/pagamentos/999").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
        }
    }

    @WebMvcTest(controllers = VendaController.class)
    public static class VendaControllerTest {
        @Autowired MockMvc mvc;
        @Autowired ObjectMapper om;
        @MockBean VendaService vendaService;
        @MockBean VendaRepository vendaRepository;

        @Test
        void postCriarVenda_quandoOk_retorna200() throws Exception {
            when(vendaService.criarVenda(any(VendaRequest.class))).thenReturn(new VendaResponse());

            VendaRequest req = new VendaRequest();
            mvc.perform(post("/vendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.registerModule(new JavaTimeModule()).writeValueAsString(req)))
               .andExpect(status().isOk());
        }

        @Test
        void getVenda_quandoExiste_retorna200() throws Exception {
            when(vendaRepository.findById(1L)).thenReturn(Optional.of(new Venda()));
            mvc.perform(get("/vendas/1").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        }

        @Test
        void getVenda_quandoNaoExiste_retorna404() throws Exception {
            when(vendaRepository.findById(999L)).thenReturn(Optional.empty());
            mvc.perform(get("/vendas/999").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
        }
    }

    @WebMvcTest(controllers = WebhookController.class)
    public static class WebhookControllerTest {
        @Autowired MockMvc mvc;
        @Autowired ObjectMapper om;
        @MockBean PagamentoService pagamentoService;

        @Test
        void postWebhook_quandoIdAusente_retorna400() throws Exception {
            WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
            mvc.perform(post("/webhooks/mercadopago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
        }

        @Test
        void postWebhook_quandoIdPresente_chamaServicoERetorna200() throws Exception {
            WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
            dto.id = "abc123";
            mvc.perform(post("/webhooks/mercadopago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
               .andExpect(status().isOk());
            verify(pagamentoService).sincronizarStatus("abc123");
        }
    }

    // ============================= DTOs (puros) =============================
    public static class PagamentoResponseTest {
        @Test
        void dtoPagamentoResponse_camposEJackson() throws Exception {
            PagamentoResponse dto = new PagamentoResponse();
            dto.id = 10L;
            dto.vendaId = 20L;
            dto.status = "APROVADO";
            dto.valor = new BigDecimal("123.45");
            dto.linkPagamento = "https://pay/link";
            dto.criadoEm = OffsetDateTime.of(2024,1,2,3,4,5,0, ZoneOffset.UTC);

            assertThat(dto.id).isEqualTo(10L);
            assertThat(dto.vendaId).isEqualTo(20L);
            assertThat(dto.status).isEqualTo("APROVADO");
            assertThat(dto.valor).isEqualByComparingTo("123.45");
            assertThat(dto.linkPagamento).isEqualTo("https://pay/link");

            ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = om.writeValueAsString(dto);
            PagamentoResponse back = om.readValue(json, PagamentoResponse.class);
            assertThat(back.status).isEqualTo("APROVADO");
        }
    }

    public static class VendaResponseTest {
        @Test
        void dtoVendaResponse_camposEJackson() throws Exception {
            VendaResponse dto = new VendaResponse();
            dto.id = 7L;
            dto.dataCriacao = OffsetDateTime.parse("2024-07-10T10:15:30Z");
            dto.valorTotal = new BigDecimal("250.00");
            dto.status = "CRIADA";

            assertThat(dto.id).isEqualTo(7L);
            assertThat(dto.valorTotal).isEqualByComparingTo("250.00");
            assertThat(dto.status).isEqualTo("CRIADA");

            ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = om.writeValueAsString(dto);
            VendaResponse back = om.readValue(json, VendaResponse.class);
            assertThat(back.status).isEqualTo("CRIADA");
        }
    }

    public static class VendaRequestTest {
        @Test
        void dtoVendaRequest_camposEJackson() throws Exception {
            VendaRequest.Item i = new VendaRequest.Item();
            i.produtoId = 1L;
            i.quantidade = 2;
            i.precoUnitario = new BigDecimal("10.50");

            VendaRequest req = new VendaRequest();
            req.clienteId = 12L;
            req.itens = List.of(i);

            assertThat(req.clienteId).isEqualTo(12L);
            assertThat(req.itens).hasSize(1);
            assertThat(req.itens.get(0).precoUnitario).isEqualByComparingTo("10.50");

            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(req);
            VendaRequest back = om.readValue(json, VendaRequest.class);
            assertThat(back.clienteId).isEqualTo(12L);
            assertThat(back.itens).hasSize(1);
        }
    }

    public static class WebhookMercadoPagoDTOTest {
        @Test
        void dtoWebhook_camposEJackson() throws Exception {
            WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
            dto.type = "payment";
            dto.id = "xyz";
            dto.topic = "payment";

            assertThat(dto.type).isEqualTo("payment");
            assertThat(dto.id).isEqualTo("xyz");
            assertThat(dto.topic).isEqualTo("payment");

            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(dto);
            WebhookMercadoPagoDTO back = om.readValue(json, WebhookMercadoPagoDTO.class);
            assertThat(back.id).isEqualTo("xyz");
        }
    }

    // ============================ ENTITIES (JPA) ============================
    @DataJpaTest
    @ActiveProfiles("test")
    public static class EntityMappingTest {
        @Autowired TestEntityManager em;

        @Test
        void persistCliente_geraId() {
            Cliente c = new Cliente();
            c.setNome("Ana");
            c.setEmail("ana@example.com");
            c.setDocumento("123");
            c.setTelefone("1199");
            Cliente salvo = em.persistFlushFind(c);
            assertThat(salvo.getId()).isNotNull();
        }

        @Test
        void persistProduto_geraId() {
            Produto p = new Produto();
            p.setNome("Notebook");
            p.setPreco(new BigDecimal("4500.00"));
            Produto salvo = em.persistFlushFind(p);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getPreco()).isEqualByComparingTo("4500.00");
        }

        @Test
        void persistVenda_statusValorCliente() {
            Venda v = new Venda();
            v.setStatus(VendaStatus.PENDENTE);
            v.setValorTotal(new BigDecimal("99.90"));
            v.setClienteId(1L);
            v.setDataCriacao(OffsetDateTime.parse("2024-01-01T12:00:00Z"));
            Venda salvo = em.persistFlushFind(v);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getStatus()).isEqualTo(VendaStatus.PENDENTE);
        }

        @Test
        void persistItemVenda_camposBasicos() {
            ItemVenda item = new ItemVenda();
            item.setProdutoId(5L);
            item.setQuantidade(2);
            item.setPrecoUnitario(new BigDecimal("10.00"));
            item.setSubtotal(new BigDecimal("20.00"));
            ItemVenda salvo = em.persistFlushFind(item);
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getSubtotal()).isEqualByComparingTo("20.00");
        }

        @Test
        void persistPagamento_enumETimestamps() {
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
        }
    }

    public static class EnumsSmokeTest {
        @Test
        void pagamentoStatusValues() {
            assertThat(PagamentoStatus.values()).contains(
                PagamentoStatus.PROCESSANDO, PagamentoStatus.APROVADO, PagamentoStatus.REJEITADO);
        }
        @Test
        void vendaStatusValues() {
            assertThat(VendaStatus.values()).contains(
                VendaStatus.PENDENTE, VendaStatus.APROVADO, VendaStatus.RECUSADO);
        }
    }

    // ========================= REPOSITORIES (JPA) ===========================
    @DataJpaTest
    @ActiveProfiles("test")
    public static class PagamentoRepositoryTest {
        @Autowired PagamentoRepository pagamentoRepository;

        private Pagamento novo() {
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
        void crudFlow() {
            Pagamento salvo = pagamentoRepository.save(novo());
            assertThat(salvo.getId()).isNotNull();

            Optional<Pagamento> found = pagamentoRepository.findById(salvo.getId());
            assertThat(found).isPresent();

            found.get().setStatus(PagamentoStatus.APROVADO);
            pagamentoRepository.save(found.get());
            assertThat(pagamentoRepository.findById(salvo.getId()).orElseThrow().getStatus())
                .isEqualTo(PagamentoStatus.APROVADO);

            assertThat(pagamentoRepository.findAll()).isNotEmpty();
            pagamentoRepository.deleteById(salvo.getId());
            assertThat(pagamentoRepository.findById(salvo.getId())).isEmpty();
        }
    }

    @DataJpaTest
    @ActiveProfiles("test")
    public static class VendaRepositoryTest {
        @Autowired VendaRepository vendaRepository;

        private Venda nova() {
            Venda v = new Venda();
            v.setStatus(VendaStatus.PENDENTE);
            v.setValorTotal(new BigDecimal("120.00"));
            v.setClienteId(7L);
            v.setDataCriacao(OffsetDateTime.parse("2024-06-01T08:30:00Z"));
            return v;
        }

        @Test
        void crudFlow() {
            Venda salva = vendaRepository.save(nova());
            assertThat(salva.getId()).isNotNull();

            Optional<Venda> found = vendaRepository.findById(salva.getId());
            assertThat(found).isPresent();

            found.get().setStatus(VendaStatus.APROVADO);
            vendaRepository.save(found.get());
            assertThat(vendaRepository.findById(salva.getId()).orElseThrow().getStatus())
                .isEqualTo(VendaStatus.APROVADO);

            assertThat(vendaRepository.findAll()).isNotEmpty();
            vendaRepository.deleteById(salva.getId());
            assertThat(vendaRepository.findById(salva.getId())).isEmpty();
        }
    }

    // ===================== SERVICES (JUnit + Mockito) =======================
    @ExtendWith(MockitoExtension.class)
    public static class PagamentoServiceTest {
        @Mock PagamentoRepository pagamentoRepository;
        @Mock VendaRepository vendaRepository;
        @Mock MercadoPagoClient mercadoPagoClient;
        @InjectMocks PagamentoService pagamentoService;

        @Test
        void criarPagamentoParaVenda_devePersistirEChamarGateway() {
            Venda venda = new Venda();
            venda.setId(10L);
            venda.setValorTotal(new BigDecimal("25.50"));

            MercadoPagoPaymentResponse mpResp = new MercadoPagoPaymentResponse();
            mpResp.paymentId = "pay_123";
            mpResp.status = "approved";
            mpResp.paymentLink = "https://mp/pay/pay_123";

            when(mercadoPagoClient.criarPagamento(any(BigDecimal.class), anyString())).thenReturn(mpResp);
            when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(inv -> {
                Pagamento p = inv.getArgument(0);
                p.setId(1L);
                return p;
            });

            Pagamento criado = pagamentoService.criarPagamentoParaVenda(venda);

            assertThat(criado.getId()).isEqualTo(1L);
            ArgumentCaptor<Pagamento> cap = ArgumentCaptor.forClass(Pagamento.class);
            org.mockito.Mockito.verify(pagamentoRepository).save(cap.capture());
            Pagamento salvo = cap.getValue();
            assertThat(salvo.getVendaId()).isEqualTo(10L);
            assertThat(salvo.getValor()).isEqualByComparingTo("25.50");
            org.mockito.Mockito.verify(mercadoPagoClient).criarPagamento(new BigDecimal("25.50"), "VENDA-10");
        }

        @Test
        void atualizarStatus_quandoExiste_persisteNovoStatus() {
            Pagamento p = new Pagamento();
            p.setId(5L);
            p.setStatus(PagamentoStatus.PROCESSANDO);
            when(pagamentoRepository.findById(5L)).thenReturn(Optional.of(p));

            pagamentoService.atualizarStatus(5L, PagamentoStatus.APROVADO);

            assertThat(p.getStatus()).isEqualTo(PagamentoStatus.APROVADO);
            org.mockito.Mockito.verify(pagamentoRepository).save(p);
        }
    }

    @ExtendWith(MockitoExtension.class)
    public static class VendaServiceTest {
        @Mock VendaRepository vendaRepository;
        @Mock PagamentoService pagamentoService;
        @InjectMocks VendaService vendaService;

        @Test
        void criarVenda_calculaTotal_persiste_eAcionaPagamento() {
            VendaRequest.Item i1 = new VendaRequest.Item();
            i1.produtoId = 1L; i1.quantidade = 2; i1.precoUnitario = new BigDecimal("10.00");
            VendaRequest.Item i2 = new VendaRequest.Item();
            i2.produtoId = 2L; i2.quantidade = 1; i2.precoUnitario = new BigDecimal("5.50");

            VendaRequest req = new VendaRequest();
            req.clienteId = 77L; req.itens = List.of(i1, i2);

            when(vendaRepository.save(any(Venda.class))).thenAnswer(inv -> {
                Venda v = inv.getArgument(0); v.setId(123L); return v;
            });

            VendaResponse resp = vendaService.criarVenda(req);

            ArgumentCaptor<Venda> cap = ArgumentCaptor.forClass(Venda.class);
            org.mockito.Mockito.verify(vendaRepository, atLeastOnce()).save(cap.capture());
            Venda salvo = cap.getValue();
            assertThat(salvo.getClienteId()).isEqualTo(77L);
            assertThat(salvo.getValorTotal()).isEqualByComparingTo("25.50");
            org.mockito.Mockito.verify(pagamentoService).criarPagamentoParaVenda(salvo);

            assertThat(resp).isNotNull();
            assertThat(resp.id).isEqualTo(123L);
            assertThat(resp.valorTotal).isEqualByComparingTo("25.50");
        }

        @Test
        void atualizarStatusVenda_persisteNovoStatus() {
            Venda v = new Venda();
            v.setId(9L);
            v.setStatus(VendaStatus.PENDENTE);
            when(vendaRepository.findById(9L)).thenReturn(Optional.of(v));

            vendaService.atualizarStatusVenda(9L, VendaStatus.APROVADO);

            assertThat(v.getStatus()).isEqualTo(VendaStatus.APROVADO);
            org.mockito.Mockito.verify(vendaRepository).save(v);
        }
    }

    // ================= INTEGRAÇÃO (Spring Boot Test) =======================
    @SpringBootTest(classes = MercadoPagoClientIT.TestApp.class)
    @Import(MercadoPagoClient.class)
    public static class MercadoPagoClientIT {

        @SpringBootApplication
        static class TestApp {}

        @Nested
        @DisplayName("Quando NÃO há propriedade definida")
        class DefaultToken {
            @Autowired MercadoPagoClient client;

            @Test
            void usaDummyTokenPorPadrao() throws Exception {
                Field f = MercadoPagoClient.class.getDeclaredField("accessToken");
                f.setAccessible(true);
                Object val = f.get(client);
                assertThat(val).isEqualTo("dummy-token");
            }
        }

        @Nested
        @TestPropertySource(properties = "mercadopago.access.token=token-123")
        @DisplayName("Quando a propriedade está definida")
        class CustomToken {
            @Autowired MercadoPagoClient client;

            @Test
            void injetaTokenConfigurado() throws Exception {
                Field f = MercadoPagoClient.class.getDeclaredField("accessToken");
                f.setAccessible(true);
                Object val = f.get(client);
                assertThat(val).isEqualTo("token-123");
            }
        }
    }
}
