## Pacote

### `dto`

**Responsabilidade**  
- Objetos de transferência de dados (Data Transfer Objects).  
- Estruturar as entradas e saídas da API, isolando detalhe de persistência/internos.

**Principais componentes esperados**  
- DTOs de requisição: ex: `VendaRequest`, `PagamentoRequest`.  
- DTOs de resposta: `VendaResponse`, `PagamentoResponse`.  
- DTO para webhook: `WebhookMercadoPagoDTO`.  
- Validações nos DTOs (`@NotNull`, `@Size`, etc.).