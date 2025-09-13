package com.example.paymentmicroservice.controller; //Declara o pacote onde está a classe VendaController

import com.example.paymentmicroservice.dto.VendaRequest; //Importa o DTO VendaRequest
import com.example.paymentmicroservice.dto.VendaResponse;//Importa o DTO VendaResponse
import com.example.paymentmicroservice.entity.Pagamento; //Importa a entidade Pagamento
import com.example.paymentmicroservice.repository.PagamentoRepository;//Importa o repositório PagamentoRepository
import com.example.paymentmicroservice.repository.VendaRepository;//Importa o repositório VendaRepository
import com.example.paymentmicroservice.service.VendaService;//Importa o serviço VendaService
import org.springframework.http.ResponseEntity;//Importa a classe ResponseEntity para manipulação de respostas HTTP
import org.springframework.web.bind.annotation.*;//Importa anotações do Spring para criação de controladores REST

@RestController //Indica que esta classe é um controlador REST
@RequestMapping("/vendas")//Define o caminho base para os endpoints deste controlador
public class VendaController { //Declara a classe VendaController
    private final VendaService vendaService;//Declara uma variável para o serviço de vendas
    private final VendaRepository vendaRepository;//Declara uma variável para o repositório de vendas   
    private final PagamentoRepository pagamentoRepository;//Declara uma variável para o repositório de pagamentos

    public VendaController(VendaService vendaService, VendaRepository vendaRepository, PagamentoRepository pagamentoRepository) { //Construtor que injeta o serviço de vendas e os repositórios
        this.vendaService = vendaService;//Inicializa o serviço de vendas
        this.vendaRepository = vendaRepository;//Inicializa o repositório de vendas
        this.pagamentoRepository = pagamentoRepository; //Inicializa o repositório de pagamentos
    }

    @PostMapping //Define um endpoint POST para criar uma nova venda
    public ResponseEntity<VendaResponse> criarVenda(@RequestBody VendaRequest request) {//Método que recebe o corpo da requisição como um objeto VendaRequest
        VendaResponse resp = vendaService.criarVenda(request);//Chama o serviço para criar a venda e obtém a resposta
        return ResponseEntity.ok(resp);//Retorna a resposta com status 200 OK
    }

    @GetMapping("/{id}") //Define um endpoint GET para buscar uma venda por ID
    public ResponseEntity<?> getVenda(@PathVariable Long id) { //Método que recebe o ID da venda como parâmetro de caminho
        return vendaRepository.findById(id).map(v -> ResponseEntity.ok(v)) //Busca a venda no repositório e retorna 200 OK se encontrada
                .orElseGet(() -> ResponseEntity.notFound().build());//Retorna 404 Not Found se a venda não for encontrada
    }
}
