package com.financescheduler.repository;
 
import com.financescheduler.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.time.LocalDate;
 
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);

    @Query("SELECT t FROM Transaction t WHERE " +
           // Busca por número de conta
           "((:criteria = 'all' AND (LOWER(t.origin.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.destination.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')))) OR " +
           "(:criteria = 'origin' AND LOWER(t.origin.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))) OR " +
           "(:criteria = 'destination' AND LOWER(t.destination.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')))) OR " +
           
           // Busca por data de transferência
           "(:criteria = 'transferDate' AND (:startDate IS NULL OR t.transferDate >= :startDate) AND (:endDate IS NULL OR t.transferDate <= :endDate)) OR " +
           
           // Busca por data de criação
           "(:criteria = 'createdAt' AND (:startDate IS NULL OR t.createdAt >= :startDate) AND (:endDate IS NULL OR t.createdAt <= :endDate))"
    )
    Page<Transaction> searchTransactions(
        @Param("query") String query, 
        @Param("criteria") String criteria,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );
}