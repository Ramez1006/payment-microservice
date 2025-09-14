## Pacote

### `integration`

**Responsabilidade**  
- Comunicação com sistemas externos, no caso Mercado Pago.  
- Encapsular SDK ou API externa, fazendo a ponte entre o serviço de negócio interno e o provedor de pagamentos.

**Principais componentes esperados**  
- Classe que chama o SDK do Mercado Pago.  
- Configurações de credenciais (token, chaves).  
- Transformações de dados entre formato de domínio e formato exigido pela API externa.  
- Tratamento de respostas externas, erros, mapeamento de status de pagamento.