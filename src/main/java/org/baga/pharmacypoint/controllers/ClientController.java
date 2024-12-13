package org.baga.pharmacypoint.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.repos.BestOffersReportRepository;
import org.baga.pharmacypoint.services.DiscountsService;
import org.baga.pharmacypoint.services.ProductsService;
import org.baga.pharmacypoint.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/client")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {
    private final ProductsService productsService;
    private final DiscountsService discountsService;
    private final BestOffersReportRepository bestOffersReportRepository;
    private final SalesService salesService;

    @GetMapping("")
    public String index(HttpSession session) {
        if ("client".equals(session.getAttribute("role"))) {
            return "client/index-client";
        } else {
            return "redirect:/login-client";
        }
    }

    @GetMapping("/read-products")
    public String readProducts(Model model, HttpSession session) {
        if ("client".equals(session.getAttribute("role"))) {
            model.addAttribute("products", productsService.readAll());
            return "client/read-products";
        } else {
            return "redirect:/login-client";
        }
    }

    @GetMapping("/read-discounts")
    public String readDiscounts(Model model, HttpSession session) {
        if ("client".equals(session.getAttribute("role"))) {
            model.addAttribute("discounts", discountsService.readAll());
            return "client/read-discounts";
        } else {
            return "redirect:/login-client";
        }
    }

    @GetMapping("/best-offers-report")
    public String bestOffersReport(Model model, HttpSession session) {
        if ("client".equals(session.getAttribute("role"))) {
            model.addAttribute("bestOffersReport", bestOffersReportRepository.getReport());
            return "client/best-offers-report";
        } else {
            return "redirect:/login-client";
        }
    }

    @PostMapping("/buy-product")
    public String buyProduct(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpSession session) {
        if ("client".equals(session.getAttribute("role"))) {
            Integer clientId = (Integer) session.getAttribute("clientId");
            salesService.buyProduct(productId, quantity, clientId);
            return "redirect:/client/read-products";
        } else {
            return "redirect:/login-client";
        }
    }
}
