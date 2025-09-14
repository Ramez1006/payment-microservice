package com.example.paymentmicroservice.test;

import com.example.paymentmicroservice.dto.VendaRequest;
import com.example.paymentmicroservice.dto.VendaResponse;
import com.example.paymentmicroservice.repository.VendaRepository;
import com.example.paymentmicroservice.service.VendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VendaController.class)
class VendaControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    VendaService vendaService;

    @MockBean
    VendaRepository vendaRepository;

    @Test
    void postCriarVenda_quandoOk_retorna200() throws Exception {
        Mockito.when(vendaService.criarVenda(ArgumentMatchers.any(VendaRequest.class)))
               .thenReturn(new VendaResponse());

        VendaRequest req = new VendaRequest();
        mvc.perform(post("/vendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req)))
           .andExpect(status().isOk());
    }

    @Test
    void getVenda_quandoExiste_retorna200() throws Exception {
        Mockito.when(vendaRepository.findById(1L)).thenReturn(Optional.of(new Object()));

        mvc.perform(get("/vendas/1").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    void getVenda_quandoNaoExiste_retorna404() throws Exception {
        Mockito.when(vendaRepository.findById(999L)).thenReturn(Optional.empty());

        mvc.perform(get("/vendas/999").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }
}
