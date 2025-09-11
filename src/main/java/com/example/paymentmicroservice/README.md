# Microserviço de Pagamentos com Mercado Pago

## Visão Geral do Fluxo

1.  **Cliente** manda uma venda: produto, valor, dados mínimos para
    cobrança.
2.  **Seu microserviço** valida e **salva** a venda no banco.
3.  Ele **cria/aciona um pagamento** via Mercado Pago (SDK oficial em
    Java).
4.  Recebe uma **resposta imediata** (ex:
    "processando/pendente/aprovado/negado").
5.  Atualiza o **status** da venda/pagamento no banco.
6.  Mais tarde, o Mercado Pago envia uma **notificação (webhook)** com o
    status final → seu serviço recebe essa notificação e **sincroniza**
    o status.

------------------------------------------------------------------------

## O que Você Precisa Ter

-   **Java 17+** (ou 11+, mas prefira 17).
-   **Maven** ou **Gradle** para gerenciar dependências.
-   **Framework**: Spring Boot (facilita REST, validação, logs,
    segurança).
-   **Banco de dados**: PostgreSQL ou MySQL (com JPA/Hibernate).
-   **SDK Java do Mercado Pago** (biblioteca oficial).
-   **Variáveis de ambiente** para segredos (ACCESS_TOKEN do Mercado
    Pago, chaves, URL pública do webhook etc.).
-   **Ferramenta de testes** (JUnit), **logs** (SLF4J/Logback), e
    **documentação da API** (OpenAPI/Swagger).
-   **Túnel de desenvolvimento** (ngrok ou similar) para testar o
    webhook localmente.

------------------------------------------------------------------------

## Desenho de Microserviço






































//# Payment Microservice - skeleton

//Projeto exemplo gerado automaticamente contendo:
//- Spring Boot + JPA + H2
//- Endpoints: /vendas, /pagamentos, /webhooks/mercadopago
//- Cliente simulado para Mercado Pago (substitua pela SDK oficial)

//Build:
//mvn clean package
//Run:
//java -jar target/payment-microservice-0.0.1-SNAPSHOT.jar
