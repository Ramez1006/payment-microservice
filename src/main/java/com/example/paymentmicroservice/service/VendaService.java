package com.example.paymentmicroservice.service;

import com.example.paymentmicroservice.dto.VendaRequest;
import com.example.paymentmicroservice.dto.VendaResponse;
import com.example.paymentmicroservice.entity.ItemVenda;
import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.entity.PagamentoStatus;
import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.entity.VendaStatus;
import com.example.paymentmicroservice.repository.VendaRepository;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class VendaService {
    private final VendaRepository vendaRepository;
    private final PagamentoService pagamentoService;
    private final PagamentoRepository pagamentoRepository;

    // Construtor com injeção de dependências
    public VendaService(VendaRepository vendaRepository, PagamentoService pagamentoService, PagamentoRepository pagamentoRepository) {
        this.vendaRepository = vendaRepository;
        this.pagamentoService = pagamentoService;
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * Cria uma venda a partir do DTO VendaRequest:
     * - Monta os itens da venda
     * - Calcula o valor total
     * - Define status inicial como PENDENTE
     * - Salva no banco
     * - Gera o pagamento vinculado à venda
     * - Retorna a resposta no formato DTO VendaResponse
     */
    @Transactional
    public VendaResponse criarVenda(VendaRequest request) {
        Venda venda = new Venda();
        BigDecimal total = BigDecimal.ZERO;

        // Monta os itens da venda a partir do request
        for (VendaRequest.Item it : request.itens) {
            ItemVenda item = new ItemVenda();
            item.setProdutoId(it.produtoId);
            item.setQuantidade(it.quantidade);
            item.setPrecoUnitario(it.precoUnitario);

            // Subtotal = preço unitário * quantidade
            item.setSubtotal(it.precoUnitario.multiply(new BigDecimal(it.quantidade)));

            // Adiciona item à lista da venda
            venda.getItens().add(item);

            // Soma subtotal ao total da venda
            total = total.add(item.getSubtotal());
        }

        // Define informações da venda
        venda.setValorTotal(total);             // valor total calculado
        venda.setClienteId(request.clienteId);  // cliente que está comprando
        venda.setStatus(VendaStatus.PENDENTE);  // status inicial

        // Salva venda no banco
        venda = vendaRepository.save(venda);

        // Cria e associa um pagamento à venda
        Pagamento pagamento = pagamentoService.criarPagamentoParaVenda(venda);

        // Monta objeto de resposta para API
        VendaResponse resp = new VendaResponse();
        resp.id = venda.getId();
        resp.dataCriacao = venda.getDataCriacao();
        resp.valorTotal = venda.getValorTotal();
        resp.status = venda.getStatus().name();

        return resp;
    }
}
