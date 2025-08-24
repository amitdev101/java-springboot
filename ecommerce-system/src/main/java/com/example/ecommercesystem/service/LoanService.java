package com.example.ecommercesystem.service;

import com.example.ecommercesystem.model.Loan;

import java.util.List;

/**
 * Non-breaking interface that mirrors the current LoanService public API.
 * This lets consumers depend on abstractions while your existing class keeps working.
 *
 * Later (optional): rename this to LoanService (interface) and rename the class to DefaultLoanService.
 */
public interface LoanService {

    List<Loan> getAllLoans();

    Loan getLoanById(Long id);

    Loan saveLoan(Loan loan);

    void deleteLoan(Long id);
}
