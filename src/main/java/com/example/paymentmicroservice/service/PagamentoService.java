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

    // Construtor com injeção de dependências (Spring injeta os repositórios e o client)
    public PagamentoService(PagamentoRepository pagamentoRepository, VendaRepository vendaRepository, MercadoPagoClient mercadoPagoClient) {
        this.pagamentoRepository = pagamentoRepository;
        this.vendaRepository = vendaRepository;
        this.mercadoPagoClient = mercadoPagoClient;
    }

    /**
     * Cria um pagamento para uma venda.
     * - Cria a entidade Pagamento localmente (status PROCESSANDO).
     * - Chama o cliente Mercado Pago (simulado) para gerar um pagamento.
     * - Atualiza o pagamento com ID e link do Mercado Pago.
     *
     * @param venda venda para a qual o pagamento será criado
     * @return pagamento criado e salvo no banco
     */
    @Transactional
    public Pagamento criarPagamentoParaVenda(Venda venda) {
        Pagamento pagamento = new Pagamento();
        pagamento.setVendaId(venda.getId());              // Relaciona o pagamento com a venda
        pagamento.setValor(venda.getValorTotal());        // Valor total da venda
        pagamento.setMetodo("MERCADOPAGO");               // Método de pagamento
        pagamento.setStatus(PagamentoStatus.PROCESSANDO); // Inicialmente em processamento
        pagamento = pagamentoRepository.save(pagamento);  // Salva no banco de dados

        // Simula chamada ao Mercado Pago para criar um pagamento remoto
        MercadoPagoClient.MercadoPagoPaymentResponse resp =
                mercadoPagoClient.createPayment("venda-" + venda.getId(), pagamento.getValor().doubleValue());

        // Atualiza o pagamento com informações retornadas pelo Mercado Pago
        pagamento.setMercadoPagoPaymentId(resp.paymentId);
        pagamento.setLinkPagamento(resp.paymentLink);
        pagamento.setStatus(PagamentoStatus.PROCESSANDO);
        pagamentoRepository.save(pagamento);

        return pagamento;
    }

    /**
     * Sincroniza o status de um pagamento com o Mercado Pago.
     * - Consulta o status do pagamento remoto.
     * - Atualiza o pagamento localmente com base na resposta (APROVADO, REJEITADO ou PROCESSANDO).
     *
     * @param mercadoPagoId identificador do pagamento no Mercado Pago
     * @return pagamento atualizado ou null se não encontrado
     */
    @Transactional
    public Pagamento sincronizarStatus(String mercadoPagoId) {
        // Busca status no Mercado Pago (simulado)
        MercadoPagoClient.MercadoPagoPaymentResponse resp = mercadoPagoClient.getPayment(mercadoPagoId);

        // Procura o pagamento local associado a esse ID do Mercado Pago
        Pagamento pag = pagamentoRepository.findAll().stream()
                .filter(p -> mercadoPagoId.equals(p.getMercadoPagoPaymentId()))
                .findFirst().orElse(null);

        if (pag == null) return null; // Nenhum pagamento encontrado

        // Atualiza o status com base na resposta do Mercado Pago
        if ("approved".equalsIgnoreCase(resp.status)) {
            pag.setStatus(PagamentoStatus.APROVADO);
        } else if ("rejected".equalsIgnoreCase(resp.status) || "refused".equalsIgnoreCase(resp.status)) {
            pag.setStatus(PagamentoStatus.REJEITADO);
        } else {
            pag.setStatus(PagamentoStatus.PROCESSANDO);
        }

        pagamentoRepository.save(pag); // Persiste a atualização no banco
        return pag;
    }
}
