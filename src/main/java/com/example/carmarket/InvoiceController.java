package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/new")
    public String showCreateInvoiceForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("brands", carBrandService.getAllBrands());
        return "create_invoice";
    }

    @PostMapping("/save")
    public String saveInvoice(
            @ModelAttribute("invoice") Invoice invoice,
            @RequestParam("selectedParts") List<Long> selectedParts,
            @RequestParam("quantities") List<Integer> quantities,
            Model model) {

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (int i = 0; i < selectedParts.size(); i++) {
            Long partId = selectedParts.get(i);
            Integer requestedQuantity = quantities.get(i);

            CarPart part = carPartService.getPartById(partId);

            if (part != null) {
                if (requestedQuantity > part.getQuantity()) {
                    model.addAttribute("invoice", invoice);
                    model.addAttribute("brands", carBrandService.getAllBrands());
                    model.addAttribute("error", "Запрашиваемое количество запчасти \"" + part.getPartName() +
                            "\" превышает остаток на складе (" + part.getQuantity() + ").");
                    return "create_invoice";
                }

                InvoiceItem item = new InvoiceItem(invoice, part, requestedQuantity, part.getPrice() * requestedQuantity);
                invoiceItems.add(item);

                part.setQuantity(part.getQuantity() - requestedQuantity);
                carPartService.savePart(part);
            }
        }

        invoice.setItems(invoiceItems);
        invoice.setStatus("Ожидается оплата"); // Устанавливаем начальный статус заказа
        invoiceService.saveInvoice(invoice);
        return "redirect:/brands";
    }

    @GetMapping("/models")
    @ResponseBody
    public List<CarModel> getModelsByBrand(@RequestParam("brandId") Long brandId) {
        return carModelService.getModelsByBrand(carBrandService.getBrandById(brandId));
    }

    @GetMapping("/parts")
    @ResponseBody
    public List<CarPart> getPartsByModel(@RequestParam("modelId") Long modelId) {
        CarModel carModel = carModelService.getModelById(modelId);
        List<CarPart> parts = carPartService.getPartsByModel(carModel);
        parts.forEach(part -> {
            part.getCarModel().getModelName(); // Lazy loading
        });
        return parts;
    }

    @GetMapping("/{id}")
    public String viewInvoiceDetails(@PathVariable("id") Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        if (invoice == null) {
            model.addAttribute("error", "Заказ не найден.");
            return "orders";
        }

        double totalSum = invoice.getItems().stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();

        model.addAttribute("invoice", invoice);
        model.addAttribute("totalSum", totalSum);

        return "invoice_details";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Invoice> orders = invoiceService.getSortedInvoices();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/updateAll")
    public String updateAllStatuses(@RequestParam Map<String, String> allParams) {
        List<Invoice> invoicesToUpdate = new ArrayList<>();

        allParams.forEach((key, value) -> {
            if (key.startsWith("status[")) {
                try {
                    Long id = Long.parseLong(key.substring(7, key.length() - 1)); // Извлекаем ID
                    Invoice invoice = invoiceService.getInvoiceById(id);
                    if (invoice != null) {
                        invoice.setStatus(value); // Устанавливаем новый статус
                        invoicesToUpdate.add(invoice);
                    }
                } catch (NumberFormatException e) {
                    // Логируем ошибку для отладки, если что-то пошло не так
                    System.err.println("Ошибка парсинга ID заказа: " + key);
                }
            }
        });

        // Сохраняем все изменения в базе данных
        invoiceService.saveAllInvoices(invoicesToUpdate);
        return "redirect:/invoices/orders";
    }
}

