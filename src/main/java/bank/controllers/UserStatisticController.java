package bank.controllers;

import bank.dto.OperationDTO;
import bank.models.BankingReplenishment;
import bank.models.User;
import bank.repository.BankReplenishmentRepository;
import bank.service.CardService;
import bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserStatisticController {

    @Autowired
    BankReplenishmentRepository bankingReplenishmentRepository;

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @GetMapping("/userstatic")
    public String getUserStatic(@AuthenticationPrincipal User user, Model model) {
        List<BankingReplenishment> bankReplList = userService.userList(user);
        List<OperationDTO> operation = userService.operationList(bankReplList);
        List<String> cardsNumber = bankReplList.stream().map(x -> x.getCardFrom().getCardNumber()).distinct().collect(Collectors.toList());
        model.addAttribute("operationList", operation);
        model.addAttribute("cardList", cardsNumber);
        return "userstatic";
    }

    @PostMapping("/maxsum")
    public String postUserMaxSum(@AuthenticationPrincipal User user, @RequestParam(name = "periods") String periodsSum, Model model) {
        List<BankingReplenishment> operationList = cardService.listSumTransactions(user, periodsSum);
        List<OperationDTO> operation = userService.operationList(operationList);
        List<OperationDTO> operationSort = new ArrayList<>();
        operation.sort(new Comparator<OperationDTO>() {
            @Override
            public int compare(OperationDTO o1, OperationDTO o2) {
                return o2.getOperationSum().compareTo(o1.getOperationSum());
            }
        });
        operationSort = operation.stream().limit(5).collect(Collectors.toList());
        model.addAttribute("operationList", operationSort);
        return "userstatic";
    }

    @PostMapping("/middle")
    public String middle(@AuthenticationPrincipal User user, @RequestParam(name = "cards") String cardNumber, @RequestParam(name = "periods") String period, Model model) {
        List<BankingReplenishment> bankReplList = userService.userList(user);
        List<BankingReplenishment> userOperation = cardService.cardList(user, cardNumber, period);
        List<OperationDTO> operation = userService.operationList(userOperation);
        List<String> cardsNumber = bankReplList.stream().map(x -> x.getCardFrom().getCardNumber()).distinct().collect(Collectors.toList());
        model.addAttribute("operationList", operation);
        model.addAttribute("cardList", cardsNumber);
        return "userstatic";
    }

//    @PostMapping("/spendingPerDay")
//    public String spendingPerDay(@AuthenticationPrincipal User user, @RequestParam(name = "day") String day,
//                                 @RequestParam(name = "timeFrom") String timeFrom, @RequestParam(name = "timeTo") String timeTo, Model model) throws ParseException {
//        List<BankingReplenishment> operationList = cardService.spendPerDay(user, day, timeFrom, timeTo);
//        List<OperationDTO> operation = userService.operationList(operationList);
//        model.addAttribute("operationList", operation);
//        return "userstatic";
//    }
}

