package org.baga.pharmacypoint.controllers;

import jakarta.servlet.http.HttpSession;
import org.baga.pharmacypoint.models.Client;
import org.baga.pharmacypoint.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final ClientsService clientsService;

    @Autowired
    public AuthController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "admin/login-admin";
    }

    @PostMapping("/login-admin")
    public String loginAdmin(@RequestParam("password") String password, HttpSession session) {
        if ("admin".equals(password)) {
            session.setAttribute("role", "admin");
            return "redirect:/admin";
        } else {
            return "admin/login-admin";
        }
    }

    @GetMapping("/login-client")
    public String loginClient() {
        return "client/login-client";
    }

    @PostMapping("/login-client")
    public String loginClient(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        if ("client".equals(password)) {
            Client client = clientsService.readAll().stream()
                    .filter(c -> c.getClientEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            if (client != null) {
                session.setAttribute("role", "client");
                session.setAttribute("clientId", client.getId());
                return "redirect:/client";
            } else {
                model.addAttribute("error", "Client not found");
                return "client/login-client";
            }
        } else {
            model.addAttribute("error", "Invalid password");
            return "client/login-client";
        }
    }
}
