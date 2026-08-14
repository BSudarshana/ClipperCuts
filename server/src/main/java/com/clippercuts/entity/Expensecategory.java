package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "expensecategory")
public class Expensecategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 75)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "expensecategory")
    private Collection<Expense> expenses;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Collection<Expense> getExpenses() { return expenses; }
    public void setExpenses(Collection<Expense> expenses) { this.expenses = expenses; }
}
