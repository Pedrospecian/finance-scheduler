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
           // Account Number Search
           "((:criteria = 'all' AND (LOWER(t.origin.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.destination.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')))) OR " +
           "(:criteria = 'origin' AND LOWER(t.origin.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))) OR " +
           "(:criteria = 'destination' AND LOWER(t.destination.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')))) OR" +
           
           // Transfer Date
           "(:criteria = 'transferDate' AND :query <> '' AND t.transferDate = CAST(:query AS localdate)) OR " +
           
           // Created Date
           "(:criteria = 'createdAt' AND :query <> '' AND t.createdAt >= CAST(:query AS localdate))"
    )
    Page<Transaction> searchTransactions(
        @Param("query") String query, 
        @Param("criteria") String criteria,
        Pageable pageable
    );
}