package com.example.paymentmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal do microserviço de pagamentos.
 *
 * @SpringBootApplication:
 * - Habilita a configuração automática do Spring Boot
 * - Faz o scan de componentes (services, repositories, controllers, etc.)
 * - Marca esta classe como ponto de entrada da aplicação
 */
@SpringBootApplication
public class PaymentMicroserviceApplication {

    /**
     * Método main — ponto de entrada da aplicação.
     * O Spring Boot inicializa todo o contexto da aplicação,
     * configura o servidor embutido (Tomcat por padrão)
     * e sobe o microserviço.
     *
     * @param args argumentos de linha de comando (se houver)
     */
    public static void main(String[] args) {
        SpringApplication.run(PaymentMicroserviceApplication.class, args);
    }
}
