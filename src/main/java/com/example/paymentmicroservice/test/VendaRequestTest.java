package com.example.paymentmicroservice.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class VendaRequestTest {

    @Test
    void deveCriarComItensEChecarCampos() {
        VendaRequest.Item i1 = new VendaRequest.Item();
        i1.produtoId = 1L;
        i1.quantidade = 2;
        i1.precoUnitario = new BigDecimal("10.50");

        VendaRequest.Item i2 = new VendaRequest.Item();
        i2.produtoId = 2L;
        i2.quantidade = 1;
        i2.precoUnitario = new BigDecimal("5.00");

        VendaRequest req = new VendaRequest();
        req.clienteId = 777L;
        req.itens = List.of(i1, i2);

        assertThat(req.clienteId).isEqualTo(777L);
        assertThat(req.itens).hasSize(2);
        assertThat(req.itens.get(0).precoUnitario).isEqualByComparingTo("10.50");
    }

    @Test
    void deveSerializarEDesserializarComJackson() throws Exception {
        VendaRequest.Item item = new VendaRequest.Item();
        item.produtoId = 42L;
        item.quantidade = 3;
        item.precoUnitario = new BigDecimal("7.99");

        VendaRequest req = new VendaRequest();
        req.clienteId = 12L;
        req.itens = List.of(item);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(req);

        // Verifica estrutura básica do JSON
        Map<String,Object> map = om.readValue(json, new TypeReference<Map<String,Object>>(){});
        assertThat(map).containsKeys("clienteId", "itens");

        VendaRequest back = om.readValue(json, VendaRequest.class);
        assertThat(back.clienteId).isEqualTo(12L);
        assertThat(back.itens).hasSize(1);
        assertThat(back.itens.get(0).quantidade).isEqualTo(3);
    }
}
