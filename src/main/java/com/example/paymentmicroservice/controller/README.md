## Pacote

### `controller`

**Responsabilidade**  
- Receber requisições HTTP vindas dos clientes da API REST.  
- Mapear endpoints, convertendo requisições em DTOs, chamando serviços, retornando respostas HTTP apropriadas.

**Principais componentes esperados**  
- Controllers REST, ex: `VendaController`, `PagamentoController`, `WebhookController`.  
- Anotações como `@RestController`, `@RequestMapping`.  
- Validação de entrada (via `@Valid`, etc.).  
- Tratamento de parâmetros de rota (`@PathVariable`), query params (`@RequestParam`), corpo (`@RequestBody`).

### `webhook`

**Responsabilidade**  
- Receber notificações externas (callbacks ou webhooks) do Mercado Pago.  
- Processar essas notificações para atualizar o status dos pagamentos/vendas no sistema.

**Principais componentes esperados**  
- Controller específico para webhook: endpoint `POST /webhooks/mercadopago`.  
- DTOs para representar payload do webhook.  
- Serviços para verificar a notificação, validar assinatura, verificar se já foi processada (idempotência), atualizar estado.  
- Possivelmente log ou armazenar notificações recebidas.


### `controller`

```java
/**
 * Pacote: com.seuprojeto.controller
 * Responsabilidade:
 *   - Mapear endpoints REST para venda, pagamento e webhooks.
 *   - Validar entrada de dados.
 *   - Delegar lógica para serviços.
 * Exemplo de classes:
 *   - VendaController
 *   - PagamentoController
 *   - WebhookMercadoPagoController
 */

---