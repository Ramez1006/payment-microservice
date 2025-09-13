package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório de Venda.
 *
 * Essa interface é responsável por acessar e manipular os dados da entidade
 * Venda no banco de dados.
 *
 * Ao estender JpaRepository<Venda, Long>, herdamos automaticamente diversos métodos prontos, como:
 * - save()       → para salvar ou atualizar uma venda
 * - findById()   → para buscar uma venda pelo ID
 * - findAll()    → para listar todas as vendas
 * - deleteById() → para excluir uma venda pelo ID
 *
 * O Spring Data JPA gera a implementação em tempo de execução, então não
 * precisamos escrever código SQL manualmente para operações básicas.
 */
public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Aqui podemos adicionar consultas personalizadas (query methods).
    // Exemplo: List<Venda> findByStatus(String status);
}
