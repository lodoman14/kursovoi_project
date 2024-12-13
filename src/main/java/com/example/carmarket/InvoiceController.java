package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        List<CarPart> parts = new ArrayList<>();

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

                // Уменьшаем остаток на складе
                part.setQuantity(part.getQuantity() - requestedQuantity);
                carPartService.savePart(part);

                parts.add(part);
            }
        }

        invoice.setParts(parts);
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
            part.getCarModel().getModelName(); // Инициализация Lazy Loading
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

        // Подсчитываем общую сумму заказа
        double totalSum = invoice.getParts().stream()
                .mapToDouble(part -> part.getPrice() * part.getQuantity())
                .sum();

        model.addAttribute("invoice", invoice);
        model.addAttribute("totalSum", totalSum);

        return "invoice_details";
    }
}
