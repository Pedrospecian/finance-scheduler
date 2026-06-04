package com.financescheduler.controller;
 
import com.financescheduler.model.Account;
import com.financescheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
     
    @Autowired
    private AccountRepository accountRepository;
     
    // GET /api/accounts - Listar todas as contas
    @GetMapping
    public List<Account> listarTodos() {
        return accountRepository.findAll();
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
    public Account criar(@RequestBody Account account) {
        return accountRepository.save(account);
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