package com.example.paymentmicroservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime dataCriacao = OffsetDateTime.now();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "venda_id")
    private List<ItemVenda> itens = new ArrayList<>();
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    private VendaStatus status = VendaStatus.PENDENTE;
    private Long clienteId;

    public Venda() {}
    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public OffsetDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(OffsetDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public VendaStatus getStatus() { return status; }
    public void setStatus(VendaStatus status) { this.status = status; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda)) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
