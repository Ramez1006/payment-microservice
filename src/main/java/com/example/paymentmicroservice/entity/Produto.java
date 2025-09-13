package com.example.paymentmicroservice.entity; // Define o pacote da classe

import jakarta.persistence.*; // Importa as anotações e classes da JPA 
import java.math.BigDecimal; // Importa BigDecimal para valores monetários  
import java.util.Objects; // Importa utilitário para equals/Hashcode

// Indica que a classe é uma entidade JPA
@Entity 
// Define o nome da tabela correpondente no banco de dados
@Table(name = "produto") 
public class Produto {
    // Define a chave primária da tabela
    @Id
    // COnfigura a geração automática do ID pelo Bnaco ( auto - incremento)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do produto
    private String nome; // Nome do Produto
    private String descricao;// Descrição do produto
    private BigDecimal precoUnitario; // Preço unitário do produto
    private boolean ativo = true; // Indica se o produto está ativo ( padrão : true )

    public Produto() {} // Construtor vazio ( necessário para o JPA)
    //Construtor que inicializa os atributos principais do produto
    public Produto(String nome, String descricao, BigDecimal precoUnitario) {
        this.nome = nome; this.descricao = descricao; this.precoUnitario = precoUnitario;
    }
// Getters e Setters
    public Long getId() { return id; } // Retorna o ID
    public void setId(Long id) { this.id = id; } // Define o ID
    public String getNome() { return nome; } // RFetorna o nome
    public void setNome(String nome) { this.nome = nome; }// Define o nome
    public String getDescricao() { return descricao; } // Retorna a descrição
    public void setDescricao(String descricao) { this.descricao = descricao; }//Define a descrição
    public BigDecimal getPrecoUnitario() { return precoUnitario; }//Retorna o preço unitário
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; } // Define o preço unitário
    public boolean isAtivo() { return ativo; } // Retorna se o produto está ativo
    public void setAtivo(boolean ativo) { this.ativo = ativo; }//Define o statues ativo

    //Sobrescreve equals para comprar produtos pelo iD
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Se for o mesmo objeto, retorna true
        if (!(o instanceof Produto)) return false; // Se não for Produto, retorna FaLse
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id); // Compara pelo campo ID
    }
    //Sobrescreve hashCode para manter consistência com equals
 @Override
public int hashCode() {
    return Objects.hash(id);
}
// Sobrescre toString para representação em texto do objeto
@Override
public String toString() {
    return "Produto{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            '}';
}
}
