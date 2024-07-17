package com.example.ecommercesystem.controller;

import com.example.ecommercesystem.model.Loan;
import com.example.ecommercesystem.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan != null) {
            existingLoan.setBorrowerName(loan.getBorrowerName());
            existingLoan.setAmount(loan.getAmount());
            existingLoan.setInterestRate(loan.getInterestRate());
            existingLoan.setDuration(loan.getDuration());
            return loanService.saveLoan(existingLoan);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
