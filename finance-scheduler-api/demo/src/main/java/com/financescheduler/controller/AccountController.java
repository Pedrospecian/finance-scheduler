package com.financescheduler.controller;
 
import com.financescheduler.model.Account;
import com.financescheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
 
import java.util.List;
import java.util.Optional;
import java.util.Map;
 
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
     
    @Autowired
    private AccountRepository accountRepository;
     
    // GET /api/accounts - Listar contas
    @GetMapping
    public ResponseEntity<Page<Account>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "all") String criteria
        ) {
                
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> resultado = accountRepository.searchAccounts(query, criteria, pageable);
        
        return ResponseEntity.ok(resultado);
    }
     
    // GET /api/account/{id} - Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> buscarPorId(@PathVariable Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
     
    // POST /api/account - Criar nova conta
    @PostMapping
    public ResponseEntity<?> criarConta(@RequestBody Account newAccount) {
        Account existingAccount = accountRepository.findByAccountNumber(newAccount.getAccountNumber());
        
        if (existingAccount != null) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Já existe uma conta cadastrada com este número."));
        }

        Account savedAccount = accountRepository.save(newAccount);
        return ResponseEntity.ok(savedAccount);
    }
     
    // PUT /api/account/{id} - Atualizar conta
    @PutMapping("/{id}")
    public ResponseEntity<Account> atualizar(@PathVariable Long id, 
                                           @RequestBody Account accountAtualizada) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(accountAtualizada.getAccountNumber());
                    account.setBalance(accountAtualizada.getBalance());
                    return ResponseEntity.ok(accountRepository.save(account));
                })
                .orElse(ResponseEntity.notFound().build());
    }
     
    // DELETE /api/account/{id} - Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    accountRepository.delete(account);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}