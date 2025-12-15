package com.apifinanceiro.Financeiro.repository;


import com.apifinanceiro.Financeiro.domain.Category;
import com.apifinanceiro.Financeiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
}
