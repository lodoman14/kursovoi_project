package com.example.carmarket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i ORDER BY CASE i.status " +
           "WHEN 'Завершён' THEN 1 ELSE 0 END, i.id ASC")
    List<Invoice> findAllSortedByStatus();
}

