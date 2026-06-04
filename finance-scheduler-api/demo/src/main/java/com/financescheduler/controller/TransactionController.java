package com.financescheduler.controller;
 
import com.financescheduler.model.Account;
import com.financescheduler.model.Transaction;
import com.financescheduler.dto.TransactionRequestDTO;
import com.financescheduler.repository.AccountRepository;
import com.financescheduler.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
 
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
     
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;
     
    // GET /api/transactions - Listar todas as transacoes
    @GetMapping
    public ResponseEntity<Page<Transaction>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "all") String criteria
        ) {
                
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> resultado = transactionRepository.searchTransactions(query, criteria, pageable);
        
        return ResponseEntity.ok(resultado);
    }
     
    // GET /api/transaction/{id} - Buscar transacao por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> buscarPorId(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
     
    // POST /api/transaction - Criar nova transacao
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody TransactionRequestDTO payload) {
        Account originAccount = accountRepository.findByAccountNumber(payload.origin());
        Account destAccount = accountRepository.findByAccountNumber(payload.destination());

        if (originAccount == null || destAccount == null) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Conta de origem ou destino não encontrada."));
        }

        Transaction transaction = new Transaction();
        transaction.setOrigin(originAccount);
        transaction.setDestination(destAccount);
        transaction.setValue(payload.value());

        LocalDate hoje = LocalDate.now();
        LocalDate transferDate = payload.transferDate();

        long dias = ChronoUnit.DAYS.between(hoje, transferDate);

        transaction.setTransferRate(calcularTaxa(dias, payload.value()));
        
        transaction.setTransferDate(transferDate);
        transaction.setCreatedAt(hoje);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(savedTransaction);
    }
     
    // PUT /api/transaction/{id} - Atualizar transacao
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> atualizar(@PathVariable Long id, 
                                           @RequestBody Transaction transactionAtualizada) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    return ResponseEntity.ok(transactionRepository.save(transaction));
                })
                .orElse(ResponseEntity.notFound().build());
    }
     
    // DELETE /api/transaction/{id} - Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public Double calcularTaxa(long dias, Double valor) {
        if (dias == 0) {
            return 3.00 + (valor * 0.025);
        } else if (dias >= 1 && dias <= 10) {
            return 12.00;
        } else if (dias >= 11 && dias <= 20) {
            return valor * 0.082;
        } else if (dias >= 21 && dias <= 30) {
            return valor * 0.069;
        } else if (dias >= 31 && dias <= 40) {
            return valor * 0.047;
        } else if (dias >= 41 && dias <= 50) {
            return valor * 0.017;
        } else {
            throw new IllegalArgumentException("Não há taxa aplicável para a data selecionada. Por favor, escolha outra data.");
        }
    }

    @GetMapping("/utils/calcular-taxa")
    public ResponseEntity<?> obterTaxaCalculada(
            @RequestParam("transferDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transferDate,
            @RequestParam("value") Double value) {
        try {
            LocalDate hoje = LocalDate.now();
            
            long dias = ChronoUnit.DAYS.between(hoje, transferDate);
            
            if (dias < 0) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "A data escolhida não pode ser anterior à data atual. Por favor, escolha outra data."));
            }
            
            Double taxa = calcularTaxa(dias, value);
            
            return ResponseEntity.ok(Map.of(
                "dias", dias,
                "taxa", taxa
            ));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}