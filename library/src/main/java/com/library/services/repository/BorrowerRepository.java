package com.library.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.services.entity.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
