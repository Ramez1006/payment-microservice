package com.example.paymentmicroservice.dto.PagamentoResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PagamentoResponseTest {

    @Test
    void deveSetarCamposPublicosEChecarValores() {
        PagamentoResponse dto = new PagamentoResponse();
        dto.id = 10L;
        dto.vendaId = 20L;
        dto.status = "APROVADO";
        dto.valor = new BigDecimal("123.45");
        dto.linkPagamento = "https://pay/link";
        dto.criadoEm = OffsetDateTime.of(2024, 1, 2, 3, 4, 5, 0, ZoneOffset.UTC);

        assertThat(dto.id).isEqualTo(10L);
        assertThat(dto.vendaId).isEqualTo(20L);
        assertThat(dto.status).isEqualTo("APROVADO");
        assertThat(dto.valor).isEqualByComparingTo("123.45");
        assertThat(dto.linkPagamento).isEqualTo("https://pay/link");
        assertThat(dto.criadoEm.getYear()).isEqualTo(2024);
    }

    @Test
    void deveSerializarEDesserializarComJackson() throws Exception {
        PagamentoResponse dto = new PagamentoResponse();
        dto.id = 1L;
        dto.vendaId = 2L;
        dto.status = "PENDENTE";
        dto.valor = new BigDecimal("9.99");
        dto.linkPagamento = "u";
        dto.criadoEm = OffsetDateTime.parse("2024-01-01T12:00:00Z");

        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = om.writeValueAsString(dto);

        // Verifica chaves básicas
        @SuppressWarnings("unchecked")
        Map<String,Object> map = om.readValue(json, Map.class);
        assertThat(map).containsKeys("id", "vendaId", "status", "valor", "linkPagamento", "criadoEm");

        PagamentoResponse back = om.readValue(json, PagamentoResponse.class);
        assertThat(back.status).isEqualTo("PENDENTE");
        assertThat(back.valor).isEqualByComparingTo("9.99");
        assertThat(back.vendaId).isEqualTo(2L);
    }
}
