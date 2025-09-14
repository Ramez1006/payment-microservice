package com.example.paymentmicroservice.test;

import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PagamentoRepository pagamentoRepository;

    @Test
    void getPagamento_quandoExiste_retorna200() throws Exception {
        Mockito.when(pagamentoRepository.findById(1L))
               .thenReturn(Optional.of(new Pagamento()));

        mvc.perform(get("/pagamentos/1").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    void getPagamento_quandoNaoExiste_retorna404() throws Exception {
        Mockito.when(pagamentoRepository.findById(999L))
               .thenReturn(Optional.empty());

        mvc.perform(get("/pagamentos/999").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }
}
