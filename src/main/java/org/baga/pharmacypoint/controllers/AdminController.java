package org.baga.pharmacypoint.controllers;


import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.dto.SalesAnalysisReport;
import org.baga.pharmacypoint.models.Client;
import org.baga.pharmacypoint.repos.SalesAnalysisReportRepository;
import org.baga.pharmacypoint.repos.SalesForecastReportRepository;
import org.baga.pharmacypoint.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final SalesAnalysisReportRepository salesAnalysisReportRepository;
    private final ClientsService clientsService;
    private final SalesForecastReportRepository salesForecastReportRepository;

    @GetMapping("")
    public String index() {
        return "admin/index-admin";
    }

    @GetMapping("/sales-report")
    public String salesReport(Model model) {
        model.addAttribute("salesReport", salesAnalysisReportRepository.getReport());
        return "admin/sales-report";
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("client") Client client) {
        return "admin/create";
    }

    @PostMapping("")
    public String create(@ModelAttribute("client") Client client) {
        clientsService.save(client);
        return "redirect:/admin";
    }

    @GetMapping("/read-all")
    public String readAll(Model model) {
        model.addAttribute("clients", clientsService.readAll());
        return "admin/read-all";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientsService.read(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") Client client, @PathVariable("id") int id) {
        clientsService.update(client, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientsService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/forecast-report")
    public String forecastReport(Model model){
        model.addAttribute("forecastReport", salesForecastReportRepository.getReport());
        return "admin/forecast-report";
    }
}
