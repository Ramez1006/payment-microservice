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

    public VendaService(VendaRepository vendaRepository, PagamentoService pagamentoService, PagamentoRepository pagamentoRepository) {
        this.vendaRepository = vendaRepository;
        this.pagamentoService = pagamentoService;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public VendaResponse criarVenda(VendaRequest request) {
        Venda venda = new Venda();
        BigDecimal total = BigDecimal.ZERO;
        for (VendaRequest.Item it : request.itens) {
            ItemVenda item = new ItemVenda();
            item.setProdutoId(it.produtoId);
            item.setQuantidade(it.quantidade);
            item.setPrecoUnitario(it.precoUnitario);
            item.setSubtotal(it.precoUnitario.multiply(new BigDecimal(it.quantidade)));
            venda.getItens().add(item);
            total = total.add(item.getSubtotal());
        }
        venda.setValorTotal(total);
        venda.setClienteId(request.clienteId);
        venda.setStatus(VendaStatus.PENDENTE);
        venda = vendaRepository.save(venda);

        // Cria pagamento
        Pagamento pagamento = pagamentoService.criarPagamentoParaVenda(venda);
        VendaResponse resp = new VendaResponse();
        resp.id = venda.getId();
        resp.dataCriacao = venda.getDataCriacao();
        resp.valorTotal = venda.getValorTotal();
        resp.status = venda.getStatus().name();
        return resp;
    }
}
