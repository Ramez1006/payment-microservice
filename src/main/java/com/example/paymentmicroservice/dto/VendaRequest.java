package com.example.paymentmicroservice.dto; //Declara o pacote onde está a classe VendaRequest

import java.math.BigDecimal;//Importa a classe BigDecimal para representar valores monetários
import java.util.List; //Importa a classe List para representar listas

public class VendaRequest { //Declara a classe VendaRequest
    public static class Item {//Declara a classe interna Item   
        public Long produtoId; //ID do produto
        public Integer quantidade;//Quantidade do produto
        public BigDecimal precoUnitario;//Preço unitário do produto
    }
    public Long clienteId;//ID do cliente
    public List<Item> itens;//Lista de itens na venda
}
