package bank.controllers;

import bank.models.BankingReplenishment;
import bank.models.Card;
import bank.models.Currency;
import bank.repository.BankReplenishmentRepository;
import bank.repository.CardRepository;
import bank.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Controller
public class MoneyController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    BankReplenishmentRepository bankReplenishmentRepository;

    @PostMapping("/moneyreplenishment")
    public String saveEditing(@RequestParam(name = "cardidname") String id, @RequestParam(name = "moneyreplenishment") BigDecimal money,
                              @RequestParam(name = "bankcurrency") String currency) throws IOException {
        Card card = cardRepository.findById(Long.valueOf(id)).get();
        Currency currencyTo = card.getCurrency();
        Currency currencyFrom = Currency.valueOf(currency);
        BigDecimal cardMoney = CurrencyService.CurrencyExchange(currencyTo, currencyFrom, money);
        card.setUserMoney(cardMoney.add(card.getUserMoney()));
        cardRepository.save(card);
        BankingReplenishment bank = new BankingReplenishment();
        bank.setCardTo(card);
        bank.setTransactionSum(money);
        bank.setTransactionDate(new Date());
        bankReplenishmentRepository.save(bank);
        System.out.println(card.getUserMoney());
        return "cardinfo";
    }
}
