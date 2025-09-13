package com.example.paymentmicroservice.dto; //Declara o pacote onde está a classe PagamentoResponse

import java.math.BigDecimal;//Importa a classe BigDecimal para representar valores monetários
import java.time.OffsetDateTime;//Importa a classe OffsetDateTime para representar data e hora com fuso horário

public class PagamentoResponse {//Declara a classe PagamentoResponse
    public Long id; //Declara os atributos públicos da classe
    public Long vendaId; //ID da venda associada ao pagamento
    public String status;//Status do pagamento
    public BigDecimal valor;//Valor do pagamento
    public String linkPagamento;//Link para o pagamento
    public OffsetDateTime criadoEm;//Data e hora de criação do pagamento
}
