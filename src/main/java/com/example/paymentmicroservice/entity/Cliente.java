package com.example.paymentmicroservice.entity; //Declara o pacote onde está a classe Cliente

import jakarta.persistence.*;//Importa as anotações JPA para mapeamento objeto-relacional
import java.util.Objects; //Importa a classe Objects para métodos utilitários

@Entity //Indica que a classe é uma entidade JPA
@Table(name = "cliente") //Especifica o nome da tabela no banco de dados
public class Cliente { // Declara a classe Cliente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que o ID é gerado automaticamente pelo banco de dados
    private Long id; //ID do cliente
    private String nome; //Nome do cliente
    private String email; //Email do cliente
    private String documento;//Documento do cliente (CPF/CNPJ)
    private String telefone;//Telefone do cliente

    public Cliente() {} //Construtor padrão
    // getters/setters
    public Long getId() { return id; } //Getter para o ID
    public void setId(Long id) { this.id = id; } //Setter para o ID
    public String getNome() { return nome; } //Getter para o nome
    public void setNome(String nome) { this.nome = nome; }//Setter para o nome
    public String getEmail() { return email; }//Getter para o email
    public void setEmail(String email) { this.email = email; }//Setter para o email
    public String getDocumento() { return documento; }//Getter para o documento
    public void setDocumento(String documento) { this.documento = documento; }//Setter para o documento
    public String getTelefone() { return telefone; }//Getter para o telefone
    public void setTelefone(String telefone) { this.telefone = telefone; }//Setter para o telefone

    @Override public boolean equals(Object o) { //Override do método equals para comparar objetos Cliente
        if (this == o) return true;//Verifica se os objetos são o mesmo
        if (!(o instanceof Cliente)) return false; //Verifica se o objeto é uma instância de Cliente
        Cliente cliente = (Cliente) o; //Faz o cast do objeto para Cliente
        return Objects.equals(id, cliente.id);//Compara os IDs dos clientes 
    }
    @Override public int hashCode() { return Objects.hash(id); }//Override do método hashCode baseado no ID
}
