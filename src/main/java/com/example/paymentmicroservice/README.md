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

### 6) TransacaoPagamento

-   `id`, `pagamentoId`, `requestPayload`, `responsePayload`,
    `statusRetornado`, `mensagemErro`, `timestamp`

### 7) NotificacaoWebhook

-   `id`, `tipo`, `identificadorExterno`, `payload`, `processada`,
    `recebidaEm`

------------------------------------------------------------------------

## DTOs

-   **Entrada**: `VendaRequest`
-   **Saída**: `VendaResponse`, `PagamentoResponse`
-   **Webhook**: `WebhookMercadoPagoDTO`

------------------------------------------------------------------------

## Endpoints REST

-   `POST /vendas` → cria a venda e inicia o pagamento.
-   `GET /vendas/{id}` → consulta status da venda.
-   `GET /pagamentos/{id}` → consulta status do pagamento.
-   `POST /webhooks/mercadopago` → recebe notificações do MP.
-   (opcional) `POST /pagamentos/{id}/reprocessar`

------------------------------------------------------------------------

## Regras de Negócio

1.  Criar venda: validar itens, salvar.
2.  Criar pagamento: montar requisição, enviar ao MP.
3.  Tratar resposta: salvar id externo, status, links/QR.
4.  Webhook: confirmar no MP e sincronizar status.
5.  Idempotência: usar `idempotency-key`.

------------------------------------------------------------------------

## Integração Mercado Pago

-   **SDK oficial Java**
-   **Credenciais**: ACCESS_TOKEN seguro
-   **Criar pagamento**: enviar valor, descrição, `external_reference`
-   **Status**: approved, in_process, rejected
-   **Webhook**: URL pública configurada
-   **Confirmação ativa**: sempre revalidar status consultando MP

------------------------------------------------------------------------

## Banco de Dados

-   Tabelas: `produto`, `cliente`, `venda`, `item_venda`, `pagamento`,
    `transacao_pagamento`, `notificacao_webhook`
-   Relacionamentos:
    -   venda 1---N item_venda
    -   venda 1---1 pagamento
    -   pagamento 1---N transacao_pagamento

------------------------------------------------------------------------

## Segurança

-   HTTPS obrigatório
-   Segredos em variáveis de ambiente
-   Validações de entrada
-   Autenticação da sua API
-   Assinatura/validação de webhook
-   LGPD: não guardar dados sensíveis do cartão

------------------------------------------------------------------------

## Erros e Reprocessos

-   Falha na chamada: marcar como PROCESSANDO/FALHA_TEMPORARIA
-   Webhook duplicado: marcar como processado/idempotência
-   Time-outs: retry com backoff

------------------------------------------------------------------------

## Testes

-   Unitários
-   Integração
-   Contract/API
-   Fluxo feliz, pendente, rejeitado, erro
-   Idempotência

------------------------------------------------------------------------

## Passos Práticos

1.  Criar projeto Spring Boot
2.  Configurar banco e entidades
3.  Definir enums de status
4.  Criar DTOs
5.  Implementar service de venda/pagamento
6.  Subir endpoint do webhook
7.  Integrar SDK MP
8.  Adicionar logs + Swagger
9.  Testes locais + ngrok
10. Deploy (Docker)








































































//# Payment Microservice - skeleton

//Projeto exemplo gerado automaticamente contendo:
//- Spring Boot + JPA + H2
//- Endpoints: /vendas, /pagamentos, /webhooks/mercadopago
//- Cliente simulado para Mercado Pago (substitua pela SDK oficial)

//Build:
//mvn clean package
//Run:
//java -jar target/payment-microservice-0.0.1-SNAPSHOT.jar
