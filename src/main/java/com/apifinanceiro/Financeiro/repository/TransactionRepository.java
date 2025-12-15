package com.apifinanceiro.Financeiro.repository;

import com.apifinanceiro.Financeiro.domain.Transaction;
import com.apifinanceiro.Financeiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
