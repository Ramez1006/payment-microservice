package com.example.paymentmicroservice.controller;//Declara o pacote onde está a classe PagamentoController

import com.example.paymentmicroservice.entity.Pagamento;//Importa a entidade Pagamento
import com.example.paymentmicroservice.repository.PagamentoRepository;//Importa o repositório PagamentoRepository
import org.springframework.http.ResponseEntity;//Importa a classe ResponseEntity para manipulação de respostas HTTP
import org.springframework.web.bind.annotation.*;//Importa anotações do Spring para criação de controladores REST

@RestController //Indica que esta classe é um controlador REST
@RequestMapping("/pagamentos") //Define o caminho base para os endpoints deste controlador
public class PagamentoController { //Declara a classe PagamentoController
    private final PagamentoRepository pagamentoRepository;//Declara uma variável para o repositório de pagamentos

    public PagamentoController(PagamentoRepository pagamentoRepository) {//Construtor que injeta o repositório de pagamentos
        this.pagamentoRepository = pagamentoRepository;//Inicializa o repositório de pagamentos
    }

    @GetMapping("/{id}")//Define um endpoint GET para buscar um pagamento por ID
    public ResponseEntity<?> getPagamento(@PathVariable Long id) { //Método que recebe o ID do pagamento como parâmetro de caminho
        return pagamentoRepository.findById(id).map(p -> ResponseEntity.ok(p)) //Busca o pagamento no repositório e retorna 200 OK se encontrado
                .orElseGet(() -> ResponseEntity.notFound().build());//Retorna 404 Not Found se o pagamento não for encontrado
    }
}
