package com.example.paymentmicroservice.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class MercadoPagoClientIT {

    @SpringBootApplication
    static class TestApp {}

    @Nested
    @SpringBootTest(classes = TestApp.class)
    @Import(MercadoPagoClient.class)
    @DisplayName("Quando NÃO há propriedade definida")
    class DefaultToken {

        @Autowired
        MercadoPagoClient client;

        @Test
        void usaDummyTokenPorPadrao() throws Exception {
            Field f = MercadoPagoClient.class.getDeclaredField("accessToken");
            f.setAccessible(true);
            Object val = f.get(client);
            assertThat(val).isEqualTo("dummy-token");
        }
    }

    @Nested
    @SpringBootTest(classes = TestApp.class)
    @Import(MercadoPagoClient.class)
    @TestPropertySource(properties = "mercadopago.access.token=token-123")
    @DisplayName("Quando a propriedade está definida")
    class CustomToken {

        @Autowired
        MercadoPagoClient client;

        @Test
        void injetaTokenConfigurado() throws Exception {
            Field f = MercadoPagoClient.class.getDeclaredField("accessToken");
            f.setAccessible(true);
            Object val = f.get(client);
            assertThat(val).isEqualTo("token-123");
        }
    }

    // --- OPCIONAL: descomente e ajuste se você expor métodos reais no cliente ---
    // @Nested
    // @SpringBootTest(classes = TestApp.class)
    // @Import(MercadoPagoClient.class)
    // class FunctionalFlow {
    //     @Autowired MercadoPagoClient client;
    //
    //     @Test
    //     void criarEPesquisarPagamento_fluxoFeliz() {
    //         // Exemplo: ajuste os nomes conforme sua implementação real
    //         var resp = client.criarPagamento(new BigDecimal("10.00"), "Pedido #1");
    //         assertThat(resp).isNotNull();
    //         assertThat(resp.paymentId).isNotBlank();
    //
    //         var status = client.obterStatusPagamento(resp.paymentId);
    //         assertThat(status.status).isIn("in_process", "approved", "rejected");
    //     }
    // }
}
