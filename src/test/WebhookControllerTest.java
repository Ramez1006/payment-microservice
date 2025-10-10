package com.example.paymentmicroservice.test;

import com.example.paymentmicroservice.dto.WebhookMercadoPagoDTO;
import com.example.paymentmicroservice.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WebhookController.class)
class WebhookControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    PagamentoService pagamentoService;

    @Test
    void postWebhook_quandoIdAusente_retorna400() throws Exception {
        WebhookMercadoPagoDTO dto = new WebhookMercadoPagoDTO();
        // dto.id = null; // explicitamente nulo

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
