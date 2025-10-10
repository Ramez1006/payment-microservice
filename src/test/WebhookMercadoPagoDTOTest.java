package com.example.paymentmicroservice.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebhookMercadoPagoDTOTest {

    @Test
    void deveSetarCamposPublicos() {
        WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
        dto.type = "payment";
        dto.id = "abc123";
        dto.topic = "payment";

        assertThat(dto.type).isEqualTo("payment");
        assertThat(dto.id).isEqualTo("abc123");
        assertThat(dto.topic).isEqualTo("payment");
    }

    @Test
    void deveSerializarEDesserializarComJackson() throws Exception {
        WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
        dto.type = "payment";
        dto.id = "xyz";
        dto.topic = "payment";

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(dto);
        WebhookMercadoPagoDTO back = om.readValue(json, WebhookMercadoPagoDTO.class);

        assertThat(back.id).isEqualTo("xyz");
        assertThat(back.type).isEqualTo("payment");
        assertThat(back.topic).isEqualTo("payment");
    }
}
