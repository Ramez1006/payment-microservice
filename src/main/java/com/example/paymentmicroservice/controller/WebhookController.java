package com.example.paymentmicroservice.controller; //Declara o pacote onde está a classe WebhookController

import com.example.paymentmicroservice.dto.WebhookMercadoPagoDTO;//Importa o DTO WebhookMercadoPagoDTO
import com.example.paymentmicroservice.service.PagamentoService;//Importa o serviço PagamentoService
import org.springframework.http.ResponseEntity;//Importa a classe ResponseEntity para manipulação de respostas HTTP
import org.springframework.web.bind.annotation.*;//Importa anotações do Spring para criação de controladores REST

@RestController //Indica que esta classe é um controlador REST
@RequestMapping("/webhooks/mercadopago")//Define o caminho base para os endpoints deste controlador
public class WebhookController {//Declara a classe WebhookController
    private final PagamentoService pagamentoService;//Declara uma variável para o serviço de pagamentos

    public WebhookController(PagamentoService pagamentoService) {//Construtor que injeta o serviço de pagamentos
        this.pagamentoService = pagamentoService;//Inicializa o serviço de pagamentos
    }

    @PostMapping //Define um endpoint POST para receber webhooks do MercadoPago
    public ResponseEntity<?> receiveWebhook(@RequestBody WebhookMercadoPagoDTO dto) { //Método que recebe o corpo da requisição como um objeto WebhookMercadoPagoDTO
        // Em produção valide a assinatura e revalide o pagamento consultando a API do MP.
        if (dto == null || dto.id == null) { //Verifica se o DTO ou o ID são nulos
            return ResponseEntity.badRequest().build();//Retorna 400 Bad Request se forem nulos
        }
        pagamentoService.sincronizarStatus(dto.id);//Chama o serviço para sincronizar o status do pagamento
        return ResponseEntity.ok().build();//Retorna 200 OK
    }
}
