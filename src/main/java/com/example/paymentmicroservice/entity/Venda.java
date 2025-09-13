package com.example.paymentmicroservice.entity; // Define o pacote da classe

import jakarta.persistence.*; //IMporta as anotações da JPA
import java.math.BigDecimal; //Usado para representar valores monetários
import java.time.OffsetDateTime;// Representa data/hora com fuso horário
import java.util.ArrayList;// Implementação de lista dinâmica
import java.util.List;// Intereface de listas
import java.util.Objects; // Utlitário para equals/hashCode
// Indica que a classe será uma entidade JPA (Mapeada para o banco de dados)
@Entity
//Define o nome da tabela correspondente do banco de dados
@Table(name = "venda")
public class Venda {
    //Identificador único da venda (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Gerado automaticamente( auto-incremento)
    private Long id;
    private OffsetDateTime dataCriacao = OffsetDateTime.now();//Data/hora da criação da venda( inicializa automaticamente com o momento atual)
    //Relaciomamento de " um para muitos" ( uma venda tem vários itens)
    @OneToMany(cascade = CascadeType.ALL) 
    //Define a chave estrangeira "venda_id" na tabela de ItemVenda
    @JoinColumn(name = "venda_id")
    private List<ItemVenda> itens = new ArrayList<>();
    //Valor total da venda
    private BigDecimal valorTotal;
    //Status da venda, armazenado como texto (STRING) no banco
    @Enumerated(EnumType.STRING)
    private VendaStatus status = VendaStatus.PENDENTE;// Inicialmente "pendente"
    // ID do cliente associado à venda (referência externa, não é entidade aqui)
    private Long clienteId;

    public Venda() {} //Construtor vazio (Necessário para JPA)
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
// Métodos utilitários
    @Override public boolean equals(Object o) { // Compara vendas pelo ID
        if (this == o) return true; // Se for o mesmo objeto, retorna true
        if (!(o instanceof Venda)) return false;// Se não for Venda, regtorna false
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id); // Compara os IDs
    }
    @Override public int hashCode() { return Objects.hash(id); } // Gera Hash baseado no ID(coerente com equals)
}
