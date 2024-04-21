package com.challengetwo.salesmanagementsystem.salesmanagement.repository;

import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionById(Long transactionId);
}
