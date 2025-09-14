package com.example.paymentmicroservice.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class VendaResponseTest {

    @Test
    void deveSetarCamposPublicosEChecarValores() {
        VendaResponse dto = new VendaResponse();
        dto.id = 7L;
        dto.dataCriacao = OffsetDateTime.parse("2024-07-10T10:15:30Z");
        dto.valorTotal = new BigDecimal("250.00");
        dto.status = "CRIADA";

        assertThat(dto.id).isEqualTo(7L);
        assertThat(dto.dataCriacao.toString()).startsWith("2024-07-10T10:15:30");
        assertThat(dto.valorTotal).isEqualByComparingTo("250.00");
        assertThat(dto.status).isEqualTo("CRIADA");
    }

    @Test
    void deveSerializarEDesserializarComJackson() throws Exception {
        VendaResponse dto = new VendaResponse();
        dto.id = 99L;
        dto.dataCriacao = OffsetDateTime.parse("2023-12-31T23:59:59Z");
        dto.valorTotal = new BigDecimal("1000.01");
        dto.status = "CONCLUIDA";

        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = om.writeValueAsString(dto);

        @SuppressWarnings("unchecked")
        Map<String,Object> map = om.readValue(json, Map.class);
        assertThat(map).containsKeys("id", "dataCriacao", "valorTotal", "status");

        VendaResponse back = om.readValue(json, VendaResponse.class);
        assertThat(back.status).isEqualTo("CONCLUIDA");
        assertThat(back.valorTotal).isEqualByComparingTo("1000.01");
        assertThat(back.id).isEqualTo(99L);
    }
}
