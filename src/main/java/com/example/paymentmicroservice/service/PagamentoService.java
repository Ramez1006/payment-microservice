package com.example.paymentmicroservice.service;

import com.example.paymentmicroservice.entity.Pagamento;
import com.example.paymentmicroservice.entity.PagamentoStatus;
import com.example.paymentmicroservice.entity.Venda;
import com.example.paymentmicroservice.integration.MercadoPagoClient;
import com.example.paymentmicroservice.repository.PagamentoRepository;
import com.example.paymentmicroservice.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final VendaRepository vendaRepository;
    private final MercadoPagoClient mercadoPagoClient;

    public PagamentoService(PagamentoRepository pagamentoRepository, VendaRepository vendaRepository, MercadoPagoClient mercadoPagoClient) {
        this.pagamentoRepository = pagamentoRepository;
        this.vendaRepository = vendaRepository;
        this.mercadoPagoClient = mercadoPagoClient;
    }

    @Transactional
    public Pagamento criarPagamentoParaVenda(Venda venda) {
        Pagamento pagamento = new Pagamento();
        pagamento.setVendaId(venda.getId());
        pagamento.setValor(venda.getValorTotal());
        pagamento.setMetodo("MERCADOPAGO");
        pagamento.setStatus(PagamentoStatus.PROCESSANDO);
        pagamento = pagamentoRepository.save(pagamento);

        // Chamada ao Mercado Pago (simulada)
        MercadoPagoClient.MercadoPagoPaymentResponse resp = mercadoPagoClient.createPayment("venda-" + venda.getId(), pagamento.getValor().doubleValue());

        pagamento.setMercadoPagoPaymentId(resp.paymentId);
        pagamento.setLinkPagamento(resp.paymentLink);
        pagamento.setStatus(PagamentoStatus.PROCESSANDO);
        pagamentoRepository.save(pagamento);
        return pagamento;
    }

    @Transactional
    public Pagamento sincronizarStatus(String mercadoPagoId) {
        MercadoPagoClient.MercadoPagoPaymentResponse resp = mercadoPagoClient.getPayment(mercadoPagoId);
        Pagamento pag = pagamentoRepository.findAll().stream()
                .filter(p -> mercadoPagoId.equals(p.getMercadoPagoPaymentId()))
                .findFirst().orElse(null);
        if (pag == null) return null;
        if ("approved".equalsIgnoreCase(resp.status)) {
            pag.setStatus(PagamentoStatus.APROVADO);
        } else if ("rejected".equalsIgnoreCase(resp.status) || "refused".equalsIgnoreCase(resp.status)) {
            pag.setStatus(PagamentoStatus.REJEITADO);
        } else {
            pag.setStatus(PagamentoStatus.PROCESSANDO);
        }
        pagamentoRepository.save(pag);
        return pag;
    }
}
