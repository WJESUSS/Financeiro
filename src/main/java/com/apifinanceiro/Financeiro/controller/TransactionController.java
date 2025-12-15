package com.apifinanceiro.Financeiro.controller;


import com.apifinanceiro.Financeiro.domain.Transaction;
import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.service.TransactionService;
import com.apifinanceiro.Financeiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;



    @GetMapping
    public List<Transaction> list(@AuthenticationPrincipal UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        return transactionService.findByUser(user);
    }
    @PostMapping
    public Transaction save(@RequestBody Transaction transaction, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        transaction.setUser(user); // Agora funciona
        return transactionService.save(transaction);
    }

}
