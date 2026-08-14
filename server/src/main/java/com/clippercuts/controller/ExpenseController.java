package com.clippercuts.controller;

import com.clippercuts.dao.ExpenseDao;
import com.clippercuts.dao.UserDao;
import com.clippercuts.entity.Expense;
import com.clippercuts.entity.User;
import com.clippercuts.util.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired private ExpenseDao expenseDao;
    @Autowired private UserDao userDao;
    @Autowired private NumberService numberService;

    @GetMapping(produces = "application/json")
    public List<Expense> get(@RequestParam HashMap<String, String> params) {
        Stream<Expense> stream = expenseDao.findAll().stream();

        String number = params.get("number");
        String category = params.get("category");
        String from = params.get("from");
        String to = params.get("to");

        if (number != null && !number.trim().isEmpty())
            stream = stream.filter(e -> e.getExpenseNumber().toLowerCase().contains(number.toLowerCase()));
        if (category != null && !category.trim().isEmpty())
            stream = stream.filter(e -> e.getExpensecategory().getName().toLowerCase().contains(category.toLowerCase()));
        if (from != null && !from.trim().isEmpty())
            stream = stream.filter(e -> !e.getPaymentDate().isBefore(java.time.LocalDate.parse(from)));
        if (to != null && !to.trim().isEmpty())
            stream = stream.filter(e -> !e.getPaymentDate().isAfter(java.time.LocalDate.parse(to)));

        return stream.collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@Valid @RequestBody Expense expense, Authentication authentication) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        User loggedUser = userDao.findByUsername(authentication.getName());
        if (loggedUser == null){
            errors = "Logged-in user not found";
        }

        if (errors.isEmpty()) {
            expense.setId(null);
            expense.setExpenseNumber(numberService.generateExpenseNumber());
            expense.setPaidByUser(loggedUser);
            expense.setCreatedAt(LocalDateTime.now());
            expenseDao.save(expense);
        }

        response.put("id", expense.getId() == null ? "" : String.valueOf(expense.getId()));
        response.put("url", expense.getId() == null ? "" : "/expenses/" + expense.getId());
        response.put("errors", errors);
        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@Valid @RequestBody Expense incoming) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";
        Expense existing = incoming.getId() == null ? null : expenseDao.findById(incoming.getId()).orElse(null);

        if (existing == null) {
            errors = "Expense payment does not exist";
        } else {
            existing.setPaymentDate(incoming.getPaymentDate());
            existing.setAmount(incoming.getAmount());
            existing.setDescription(incoming.getDescription());
            existing.setExpensecategory(incoming.getExpensecategory());
            existing.setPaymentmethod(incoming.getPaymentmethod());
            expenseDao.save(existing);
        }

        response.put("id", incoming.getId() == null ? "" : String.valueOf(incoming.getId()));
        response.put("url", incoming.getId() == null ? "" : "/expenses/" + incoming.getId());
        response.put("errors", errors);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> delete(@PathVariable Integer id) {
        HashMap<String, String> response = new HashMap<>();
        Expense expense = expenseDao.findById(id).orElse(null);
        String errors = "";
        if (expense == null) errors = "Expense payment does not exist";
        else expenseDao.delete(expense);
        response.put("id", String.valueOf(id));
        response.put("url", "/expenses/" + id);
        response.put("errors", errors);
        return response;
    }
}
