## Pacote

### `repository`

**Responsabilidade**  
- Acesso ao banco de dados.  
- Persistência e recuperação de entidades.  

**Principais componentes esperados**  
- Interfaces que estendem `JpaRepository` ou semelhantes, por exemplo `VendaRepository`, `PagamentoRepository`.  
- Métodos de consulta customizados caso necessário (por exemplo, buscando vendas por cliente, status, etc.).
