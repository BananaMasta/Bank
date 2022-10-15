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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    @PostMapping("/save")
    public String userCard(@AuthenticationPrincipal User user, @RequestParam(name = "cardnumber") String cardNumber, @RequestParam(name = "carddate") String cardDate,
                           @RequestParam(name = "cardccv") String cardCcv) throws ParseException {
        Date date = new SimpleDateFormat("MM/yy").parse(cardDate);
        Card card = new Card(cardNumber, user, date, cardCcv, Currency.USD, null);
        cardRepository.save(card);
        return "userCard";
    }
}
