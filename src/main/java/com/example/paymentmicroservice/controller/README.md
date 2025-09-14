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

---