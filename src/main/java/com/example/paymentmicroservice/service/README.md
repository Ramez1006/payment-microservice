## Pacote

### `service`

**Responsabilidade**  
- Contém regras de negócio da aplicação.  
- Orquestra operações entre repositórios, integração externa, manipulação de dados de domínio.  
- Faz verificações (ex: validação de estado, consistência) e lógica de fluxo (ex: criar venda → iniciar pagamento → atualizar status).

**Principais componentes esperados**  
- Classes de serviço como `VendaService`, `PagamentoService`, `NotificacaoService`.  
- Métodos para: criar venda, consultar status, reprocessar pagamento, etc.  
- Lógica de idempotência, tratamento de exceções de negócio.