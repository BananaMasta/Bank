package bank.controllers;

import bank.models.Card;
import bank.models.Role;
import bank.models.User;
import bank.repository.CardRepository;
import bank.service.UserService;
import bank.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CardRepository cardRepository;

    @GetMapping("/registration")
    public String bankAccountRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "bankAccountRegistration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String createUser(User user) {
        if (!userService.registrationUser(user)) {
            return "redirect:/registration";
        } else {
            System.out.println(user);
            return "login";
        }
    }

    @GetMapping("/userbankaccount")
    public String userBankAccount(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userName", user.getSecondname());
        model.addAttribute("cardlist", cardRepository.findCardsByUser(user));
        return "userBankAccountNew";
    }

}
