package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getSortedInvoices() {
        return invoiceRepository.findAllSortedByStatus();
    }

    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public void updateInvoiceStatus(Long id, String status) {
        Invoice invoice = getInvoiceById(id);
        if (invoice != null) {
            invoice.setStatus(status);
            saveInvoice(invoice);
        }
    }
}
