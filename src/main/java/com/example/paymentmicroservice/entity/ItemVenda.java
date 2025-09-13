package com.example.paymentmicroservice.entity; //Declara o pacote onde está a classe ItemVenda

import jakarta.persistence.*; //Importa as anotações JPA para mapeamento objeto-relacional
import java.math.BigDecimal;//Importa a classe BigDecimal para valores monetários
import java.util.Objects;//Importa a classe Objects para métodos utilitários

@Entity //Indica que a classe é uma entidade JPA
@Table(name = "item_venda") //Especifica o nome da tabela no banco de dados
public class ItemVenda {// Declara a classe ItemVenda
    @Id //Indica que o campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que o ID é gerado automaticamente pelo banco de dados
    private Long id;//ID do item de venda 
    private Long produtoId; //ID do produto
    private Integer quantidade;//Quantidade do produto
    private BigDecimal precoUnitario;//Preço unitário do produto
    private BigDecimal subtotal; //Subtotal (quantidade * preço unitário)

    public ItemVenda() {} //Construtor padrão
    // getters/setters
    public Long getId() { return id; } // Constutor para o ID
    public void setId(Long id) { this.id = id; } // Contrutor para o ID
    public Long getProdutoId() { return produtoId; }// contrutor para o produtoId
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; } // contrutor para o produtoId
    public Integer getQuantidade() { return quantidade; }// contrutor para a quantidade
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }// contrutor para a quantidade
    public BigDecimal getPrecoUnitario() { return precoUnitario; }// contrutor para o precoUnitario
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }// contrutor para o precoUnitario
    public BigDecimal getSubtotal() { return subtotal; }// contrutor para o subtotal
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }// contrutor para o subtotal

    @Override public boolean equals(Object o) { //Override do método equals para comparar objetos ItemVenda
        if (this == o) return true;//Verifica se os objetos são o mesmo
        if (!(o instanceof ItemVenda)) return false;//Verifica se o objeto é uma instância de ItemVenda
        ItemVenda that = (ItemVenda) o;//Faz o cast do objeto para ItemVenda
        return Objects.equals(id, that.id); //Compara os IDs dos itens de venda
    }
    @Override public int hashCode() { return Objects.hash(id); } //Override do método hashCode baseado no ID
}
