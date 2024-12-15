package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Получить все инвойсы
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Получить отсортированные инвойсы
    public List<Invoice> getSortedInvoices() {
        return invoiceRepository.findAllSortedByStatus();
    }

    // Сохранить один инвойс
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    // Сохранить несколько инвойсов
    public void saveAllInvoices(List<Invoice> invoices) {
        invoiceRepository.saveAll(invoices);
    }

    // Получить инвойс по ID
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    // Обновить статус одного инвойса
    public void updateInvoiceStatus(Long id, String status) {
        Invoice invoice = getInvoiceById(id);
        if (invoice != null) {
            invoice.setStatus(status);
            saveInvoice(invoice);
        }
    }
}

