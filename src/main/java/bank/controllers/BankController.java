package bank.controllers;

import bank.models.BankingReplenishment;
import bank.models.Card;
import bank.repository.BankReplenishmentRepository;
import bank.repository.CardRepository;
import bank.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Controller
public class BankController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    BankReplenishmentRepository bankReplenishmentRepository;

    @GetMapping("/banktransaction")
    public String getBankTransaction() {
        return "cardTransaction";
    }

    @PostMapping("/banktransaction")
    public String postBankTransaction(@RequestParam(name = "cardnumber") String cardNumber, @RequestParam(name = "usercardid") String id, Model model) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        Card userCard = cardRepository.findById(Long.valueOf(id)).get();
        //user main card information
        model.addAttribute("mainCardUserId", userCard.getId());
        model.addAttribute("userCardNum", userCard.getCardNumber());
        model.addAttribute("mainUserFirstName", userCard.getUser().getFirstname());
        model.addAttribute("mainUserSecondName", userCard.getUser().getSecondname());
        model.addAttribute("mainUserLastName", userCard.getUser().getLastname());
        model.addAttribute("mainUserMoney", userCard.getUserMoney());
        model.addAttribute("mainUserCurrency", userCard.getCurrency());
        //user selected for transfer info
        model.addAttribute("cardid", card.getId());
        model.addAttribute("cardNum", card.getCardNumber());
        model.addAttribute("cardUserFirstName", card.getUser().getFirstname());
        model.addAttribute("cardUserSecondName", card.getUser().getSecondname());
        model.addAttribute("cardUserLastName", card.getUser().getLastname());
        model.addAttribute("cardUserCurrency", card.getCurrency());
        return "cardTransaction";
    }

    @PostMapping("/moneytransfer")
    public String TransferUserMoney(@RequestParam(name = "cardNumber") String cardNumber, @RequestParam(name = "cardNumbers") String cardNumbers,
                                    @RequestParam(name = "money") BigDecimal userMoneyToTrans) {
        Card card = cardRepository.findByCardNumber(cardNumbers);
        Card userCard = cardRepository.findByCardNumber(cardNumber);
        BigDecimal b = userCard.getUserMoney();
        BigDecimal c = card.getUserMoney();
        if (userCard.getUserMoney().compareTo(userMoneyToTrans) >= 0) {
//            CurrencyService.CurrencyExchange(currencyTo, currencyFrom, money);
            BigDecimal d = userCard.setUserMoney(b.subtract(userMoneyToTrans));
            card.setUserMoney(userMoneyToTrans.add(c));
            cardRepository.save(card);
            cardRepository.save(userCard);
            BankingReplenishment bank = new BankingReplenishment();
            bank.setCardFrom(card);
            bank.setCardTo(userCard);
            bank.setTransactionSum(userMoneyToTrans);
            bank.setTransactionDate(new Date());
            bankReplenishmentRepository.save(bank);
            System.out.println(card.getUserMoney());
            System.out.println(d);
        } else {
            System.out.println("Error");
        }
        return "cardTransaction";
    }
}
