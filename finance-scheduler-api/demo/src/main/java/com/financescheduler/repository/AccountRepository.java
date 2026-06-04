package com.financescheduler.repository;
 
import com.financescheduler.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
 
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE " +
           "(:query = '' OR " + 
           "(:criteria = 'all' AND LOWER(a.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))) OR " +
           "(:criteria = 'accountNumber' AND LOWER(a.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))))"
    )
    Page<Account> searchAccounts(
        @Param("query") String query, 
        @Param("criteria") String criteria,
        Pageable pageable
    );
}