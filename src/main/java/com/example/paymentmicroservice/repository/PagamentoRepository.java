package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório de Pagamento.
 * 
 * Essa interface é responsável por acessar e manipular os dados da entidade
 * Pagamento no banco de dados. 
 *
 * Ao estender JpaRepository, herdamos métodos prontos como:
 * - save()       → para salvar ou atualizar um pagamento
 * - findById()   → para buscar um pagamento pelo ID
 * - findAll()    → para listar todos os pagamentos
 * - deleteById() → para excluir um pagamento
 *
 * O Spring Data JPA cria automaticamente a implementação em tempo de execução.
 */
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    // Podemos adicionar consultas personalizadas aqui usando query methods, 
    // como por exemplo: List<Pagamento> findByStatus(String status);
}
