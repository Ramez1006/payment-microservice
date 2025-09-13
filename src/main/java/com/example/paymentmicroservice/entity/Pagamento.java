package com.example.paymentmicroservice.entity; //Declara o pacote onde está a classe Pagamento

import jakarta.persistence.*;  //Importa as anotações JPA para mapeamento objeto-relacional
import java.math.BigDecimal;//Importa a classe BigDecimal para valores monetários
import java.time.OffsetDateTime;//Importa a classe OffsetDateTime para data e hora com fuso horário
import java.util.Objects; //Importa a classe Objects para métodos utilitários

@Entity //Indica que a classe é uma entidade JPA
@Table(name = "pagamento") //Especifica o nome da tabela no banco de dados
public class Pagamento {// Declara a classe Pagamento
    @Id //Indica que o campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que o ID é gerado automaticamente pelo banco de dados
    private Long id; //ID do pagamento
    private Long vendaId; //ID da venda associada ao pagamento
    private String metodo;//Método de pagamento (ex: cartão, boleto)
    @Enumerated(EnumType.STRING) //Indica que o campo é um enum e deve ser armazenado como string no banco de dados
    private PagamentoStatus status; //Status do pagamento (ex: PENDENTE, APROVADO, CANCELADO)
    private BigDecimal valor; //Valor do pagamento
    private String descricao; //Descrição do pagamento
    private String mercadoPagoPaymentId; //ID do pagamento no MercadoPago
    private String linkPagamento;//Link para o pagamento (ex: link do boleto)
    private OffsetDateTime criadoEm = OffsetDateTime.now();  //Data e hora de criação do pagamento
    private OffsetDateTime atualizadoEm; //Data e hora da última atualização do pagamento

    public Pagamento() {} //Construtor padrão
    // getters/setters
    public Long getId() { return id; } //Construtor para o ID
    public void setId(Long id) { this.id = id; }//Construtor para o ID  
    public Long getVendaId() { return vendaId; }//Construtor para o vendaId
    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }//Construtor para o vendaId
    public String getMetodo() { return metodo; }//Construtor para o metodo
    public void setMetodo(String metodo) { this.metodo = metodo; }//Construtor para o metodo
    public PagamentoStatus getStatus() { return status; }//Construtor para o status
    public void setStatus(PagamentoStatus status) { this.status = status; } //Construtor para o status
    public BigDecimal getValor() { return valor; } //Construtor para o valor
    public void setValor(BigDecimal valor) { this.valor = valor; }//Construtor para o valor
    public String getDescricao() { return descricao; }//Construtor para a descricao
    public void setDescricao(String descricao) { this.descricao = descricao; } //Construtor para a descricao
    public String getMercadoPagoPaymentId() { return mercadoPagoPaymentId; } // 
    public void setMercadoPagoPaymentId(String mercadoPagoPaymentId) { this.mercadoPagoPaymentId = mercadoPagoPaymentId; } // Método setter: atribui o valor recebido (mercadoPagoPaymentId) ao atributo da classe
    public String getLinkPagamento() { return linkPagamento; } // Método getter: retorna o valor atual do atributo linkPagamento
    public void setLinkPagamento(String linkPagamento) { this.linkPagamento = linkPagamento; }// Método setter: define/atualiza o valor do atributo linkPagamento
    public OffsetDateTime getCriadoEm() { return criadoEm; }// Método getter: retorna o valor atual do atributo criadoEm (data/hora de criação)
    public void setCriadoEm(OffsetDateTime criadoEm) { this.criadoEm = criadoEm; }// Método setter: define/atualiza o valor do atributo criadoEm
    public OffsetDateTime getAtualizadoEm() { return atualizadoEm; }// Método getter: retorna o valor atual do atributo atualizadoEm (data/hora de última atualização)
    public void setAtualizadoEm(OffsetDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    @Override public boolean equals(Object o) { // Sobrescrita do método equals para comprar objetos da classe Pagamento
        if (this == o) return true; // Se  o objeto comparado for o mesmo, retorna true
        if (!(o instanceof Pagamento)) return false; // Se o obejeto não for instância de Pagamento, retorna false
        Pagamento pagamento = (Pagamento) o; // Faz o cast para o Pagamento e compara os IDs
        return Objects.equals(id, pagamento.id);
    }
    // Sobrescrita do método hsdhCode: retorna um hash baseado no ID
    @Override public int hashCode() { return Objects.hash(id); }
}
