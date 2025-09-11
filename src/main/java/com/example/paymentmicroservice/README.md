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

-   **API HTTP REST** (ex.: `/vendas`, `/pagamentos`,
    `/webhooks/mercadopago`).
-   **Camadas**:
    -   **Controller** (recebe requisições HTTP).
    -   **Service** (regras de negócio).
    -   **Repository** (acesso ao banco).
    -   **Cliente de Integração** (classe que chama o Mercado Pago via
        SDK).
-   **Mensageria (opcional)**: fila para reprocessar pagamentos ou lidar
    com picos.
-   **Observabilidade**: logs, métricas simples (tempo de chamada, taxa
    de erro).

------------------------------------------------------------------------

## Entidades (Classes)

Cada entidade deve ter: **atributos**, **construtores**, **get/set**,
**hashCode()**, **equals()**, **toString()**.

### 1) Produto

-   `id`, `nome`, `descricao`, `precoUnitario`, `ativo`

### 2) Cliente

-   `id`, `nome`, `email`, `documento`, `telefone`

### 3) Venda

-   `id`, `dataCriacao`, `itens (ItemVenda)`, `valorTotal`, `status`,
    `clienteId`

### 4) ItemVenda

-   `id`, `produtoId`, `quantidade`, `precoUnitario`, `subtotal`

### 5) Pagamento

-   `id`, `vendaId`, `metodo`, `status`, `valor`, `descricao`,
    `mercadoPagoPaymentId`, `qrCode/codigoBarras/linkPagamento`,
    `criadoEm`, `atualizadoEm`















































































//# Payment Microservice - skeleton

//Projeto exemplo gerado automaticamente contendo:
//- Spring Boot + JPA + H2
//- Endpoints: /vendas, /pagamentos, /webhooks/mercadopago
//- Cliente simulado para Mercado Pago (substitua pela SDK oficial)

//Build:
//mvn clean package
//Run:
//java -jar target/payment-microservice-0.0.1-SNAPSHOT.jar
