package com.example.paymentmicroservice.dto; //Declara o pacote onde está a classe VendaResponse

import java.math.BigDecimal; //Importa a classe BigDecimal para representar valores monetários
import java.time.OffsetDateTime;//  Importa a classe OffsetDateTime para representar data e hora com fuso horário

public class VendaResponse { //Declara a classe VendaResponse
    public Long id; //ID da venda
    public OffsetDateTime dataCriacao;//Data e hora de criação da venda
    public BigDecimal valorTotal; //Valor total da venda
    public String status;//Status da venda
}
