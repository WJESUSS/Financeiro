package com.apifinanceiro.Financeiro.service;


import com.apifinanceiro.Financeiro.domain.Transaction;
import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUser(user);
    }
}
