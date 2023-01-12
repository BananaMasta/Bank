package bank.controllers;

import bank.models.Card;
import bank.models.Currency;
import bank.models.User;
import bank.repository.CardRepository;
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
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @GetMapping("/usercard")
    public String createNewUserCard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("dateExp", CardUtil.dateExpired());
        model.addAttribute("ccv", CardUtil.rndCVSCode());
        model.addAttribute("cardNum", CardUtil.cardNumberGener());
        model.addAttribute("cardUser", user.getSecondname() + " " + user.getFirstname());
        model.addAttribute("currence", Currency.values());
        return "userCard";
    }

    @GetMapping("/userbankaccount/cardinfo/{id}")
    public String userMoneyInCard(@AuthenticationPrincipal User user, @PathVariable String id, Model model) {
        Card card = cardRepository.findById(Long.valueOf(id)).get();
        model.addAttribute("cardid", card.getId());
        model.addAttribute("cardnumbers", card.getCardNumber());
        model.addAttribute("cardmoney", card.getUserMoney());
        model.addAttribute("cardcurrency", String.valueOf(card.getCurrency()));
        model.addAttribute("carddateexpired", card.getDateExpired());
        model.addAttribute("carduser", user.getSecondname() + " " + user.getFirstname());
        return "cardinfo";
    }

    @PostMapping("/usercard")
    public String userCard(@AuthenticationPrincipal User user, @RequestParam(name = "cardnumber") String cardNumber, @RequestParam(name = "carddate") String cardDate,
                           @RequestParam(name = "cardccv") String cardCcv, @RequestParam(name = "bankcurrency") String currency) {
        Currency bankCurrency = Currency.valueOf(currency);
        Card card = new Card(cardNumber, user, cardDate, cardCcv, bankCurrency, BigDecimal.valueOf(0.00));
        cardRepository.save(card);
        return "userCard";
    }
}
