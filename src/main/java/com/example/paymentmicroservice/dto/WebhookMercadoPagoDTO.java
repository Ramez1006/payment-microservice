package com.example.paymentmicroservice.dto; //Declara o pacote onde está a classe WebhookMercadoPagoDTO

public class WebhookMercadoPagoDTO { //Declara a classe WebhookMercadoPagoDTO
    public String type; //Tipo de evento do webhook
    public String id; //ID do recurso associado ao evento
    public String topic;// Tópico do evento
}
