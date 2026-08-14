package com.clippercuts.dao;

import com.clippercuts.entity.Expense;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseDao extends JpaRepository<Expense, Integer> {
    @Override
    @EntityGraph(attributePaths = {"expensecategory", "paymentmethod", "paidByUser"})
    List<Expense> findAll();

    @Override
    @EntityGraph(attributePaths = {"expensecategory", "paymentmethod", "paidByUser"})
    Optional<Expense> findById(Integer id);

    @Query(value = "SELECT e.expense_number FROM expense e " +
            "WHERE e.expense_number LIKE CONCAT('EXP-', :year, '-%') " +
            "ORDER BY e.expense_number DESC LIMIT 1", nativeQuery = true)
    String getLastExpenseNumberByYear(@Param("year") int year);
}
