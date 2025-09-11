package com.example.paymentmicroservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendaId;
    private String metodo;
    @Enumerated(EnumType.STRING)
    private PagamentoStatus status;
    private BigDecimal valor;
    private String descricao;
    private String mercadoPagoPaymentId;
    private String linkPagamento;
    private OffsetDateTime criadoEm = OffsetDateTime.now();
    private OffsetDateTime atualizadoEm;

    public Pagamento() {}
    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVendaId() { return vendaId; }
    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public PagamentoStatus getStatus() { return status; }
    public void setStatus(PagamentoStatus status) { this.status = status; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getMercadoPagoPaymentId() { return mercadoPagoPaymentId; }
    public void setMercadoPagoPaymentId(String mercadoPagoPaymentId) { this.mercadoPagoPaymentId = mercadoPagoPaymentId; }
    public String getLinkPagamento() { return linkPagamento; }
    public void setLinkPagamento(String linkPagamento) { this.linkPagamento = linkPagamento; }
    public OffsetDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(OffsetDateTime criadoEm) { this.criadoEm = criadoEm; }
    public OffsetDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(OffsetDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
