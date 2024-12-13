package org.baga.pharmacypoint.controllers;

import jakarta.servlet.http.HttpSession;
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
    public String index(HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            return "admin/index-admin";
        } else {
            return "redirect:/login-admin";
        }
    }

    @GetMapping("/sales-report")
    public String salesReport(Model model, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            model.addAttribute("salesReport", salesAnalysisReportRepository.getReport());
            return "admin/sales-report";
        } else {
            return "redirect:/login-admin";
        }
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("client") Client client, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            return "admin/create";
        } else {
            return "redirect:/login-admin";
        }
    }

    @PostMapping("")
    public String create(@ModelAttribute("client") Client client, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            clientsService.save(client);
            return "redirect:/admin";
        } else {
            return "redirect:/login-admin";
        }
    }

    @GetMapping("/read-all")
    public String readAll(Model model, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            model.addAttribute("clients", clientsService.readAll());
            return "admin/read-all";
        } else {
            return "redirect:/login-admin";
        }
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            model.addAttribute("client", clientsService.read(id));
            return "admin/edit";
        } else {
            return "redirect:/login-admin";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") Client client, @PathVariable("id") int id, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            clientsService.update(client, id);
            return "redirect:/admin";
        } else {
            return "redirect:/login-admin";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            clientsService.delete(id);
            return "redirect:/admin";
        } else {
            return "redirect:/login-admin";
        }
    }

    @GetMapping("/forecast-report")
    public String forecastReport(Model model, HttpSession session) {
        if ("admin".equals(session.getAttribute("role"))) {
            model.addAttribute("forecastReport", salesForecastReportRepository.getReport());
            return "admin/forecast-report";
        } else {
            return "redirect:/login-admin";
        }
    }
}
