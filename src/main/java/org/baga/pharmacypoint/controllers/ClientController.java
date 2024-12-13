package org.baga.pharmacypoint.controllers;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.repos.BestOffersReportRepository;
import org.baga.pharmacypoint.services.DiscountsService;
import org.baga.pharmacypoint.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {
    private final ProductsService productsService;
    private final DiscountsService discountsService;
    private final BestOffersReportRepository bestOffersReportRepository;

    @GetMapping("")
    public String index() {
        return "client/index-client";
    }

    @GetMapping("/read-products")
    public String readProducts(Model model) {
        model.addAttribute("products", productsService.readAll());
        return "client/read-products";
    }

    @GetMapping("/read-discounts")
    public String readDiscounts(Model model) {
        model.addAttribute("discounts", discountsService.readAll());
        return "client/read-discounts";
    }

    @GetMapping("/best-offers-report")
    public String bestOffersReport(Model model) {
        model.addAttribute("bestOffersReport", bestOffersReportRepository.getReport());
        return "client/best-offers-report";
    }
}
