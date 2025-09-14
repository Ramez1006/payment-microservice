## Pacotes

### `model` ou `entity`

**Responsabilidade**  
- Definição das entidades de domínio que correspondem às tabelas no banco de dados.  
- Representam os dados persistidos.

**Principais componentes esperados**  
- Classes como `Venda`, `Pagamento`, `Cliente`, `Produto`, `ItemVenda`, `TransacaoPagamento`, `NotificacaoWebhook`.  
- Anotações JPA: `@Entity`, `@Table`, `@Column`, `@Id`, relacionamento (`@OneToMany`, etc.).  
- Métodos utilitários: `equals()`, `hashCode()`, `toString()`.  