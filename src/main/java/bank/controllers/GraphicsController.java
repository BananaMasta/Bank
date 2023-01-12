package bank.controllers;

import bank.models.BankingReplenishment;
import bank.models.Card;
import bank.models.User;
import bank.repository.CardRepository;
import bank.service.CardService;
import bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class GraphicsController {
    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;


    @GetMapping("/graphics")
    public String getGraphics(@AuthenticationPrincipal User user, Model model) {
        Map<String, BigDecimal> bankTransactionList = cardService.spendList(user);
        model.addAttribute("getDate", bankTransactionList.keySet());
        model.addAttribute("getMoney", bankTransactionList.values());
        return "graphics";
    }

    @PostMapping("/maxsuminperiod")
    public String getGraphicsPeriods(@AuthenticationPrincipal User user, @RequestParam(name = "periods") String periodsSum, Model model) {
        Map<String, BigDecimal> bankTransactionList1 = cardService.mapSumTransactions(user, periodsSum);
        model.addAttribute("getDate", bankTransactionList1.keySet());
        model.addAttribute("getMoney", bankTransactionList1.values());
        return "graphics";
    }

    @PostMapping("/middleperiod")
    public String getGraphicsMiddlePeriods(@AuthenticationPrincipal User user, @RequestParam(name = "periods") String periodsSum, Model model) {
        Map<String, BigDecimal> bankTransactionList1 = cardService.middleTransactions(user, periodsSum);
        model.addAttribute("getDate", bankTransactionList1.keySet());
        model.addAttribute("getMoney", bankTransactionList1.values());
        return "graphics";
    }
}
