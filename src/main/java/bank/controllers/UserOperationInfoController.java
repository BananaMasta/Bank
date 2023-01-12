package bank.controllers;

import bank.dto.OperationDTO;
import bank.models.BankingReplenishment;
import bank.models.User;
import bank.repository.BankReplenishmentRepository;
import bank.repository.CardRepository;
import bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserOperationInfoController {

    @Autowired
    BankReplenishmentRepository bankReplenishmentRepository;
    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserService userService;

    @GetMapping("/useroperationinfo")
    public String getUserOpInfo(@AuthenticationPrincipal User user, Model model) {
        List<BankingReplenishment> bankReplList = userService.userList(user);
        Set<String> cardTransaction = bankReplList.stream().map(x -> x.getCardFrom().getCardNumber()).collect(Collectors.toSet());
        List<OperationDTO> operation = userService.operationList(bankReplList);
        model.addAttribute("operationDTOList", operation);
        model.addAttribute("cards", cardTransaction);
        return "useroperationinfo";
    }

    @PostMapping("/sortsum")
    public String postUserOperationInfo(@AuthenticationPrincipal User user, @RequestParam(name = "sumFrom") BigDecimal sumForm,
                                        @RequestParam(name = "sumTo") BigDecimal sumTo, Model model) {

        List<BankingReplenishment> list = userService.userList(user);
        List<BankingReplenishment> sorted = list.stream()
                .filter(x -> x.getTransactionSum().doubleValue() >= sumForm.doubleValue() && x.getTransactionSum().doubleValue() <= sumTo.doubleValue())
                .collect(Collectors.toList());
        List<OperationDTO> operation = userService.operationList(sorted);
        model.addAttribute("operationDTOList", operation);
        return "useroperationinfo";
    }

    @PostMapping("/selectcard")
    public String postUsersCardInfo(@AuthenticationPrincipal User user, @RequestParam(name = "usercard") String cardNumber, Model model) {
        List<BankingReplenishment> list = userService.userList(user);
        List<BankingReplenishment> sorted = list.stream()
                .filter(x -> x.getCardFrom().getCardNumber().equals(cardNumber)).collect(Collectors.toList());
        List<OperationDTO> operation = userService.operationList(sorted);
        Set<String> cardTransaction = list.stream().map(x -> x.getCardFrom().getCardNumber()).collect(Collectors.toSet());
        model.addAttribute("operationDTOList", operation);
        model.addAttribute("cards", cardTransaction);
        return "useroperationinfo";
    }

    @PostMapping("/datesorting")
    public String postDateSorting(@AuthenticationPrincipal User user, @RequestParam(name = "dateFrom") String dateForm,
                                  @RequestParam(name = "dateTo") String dateTo, Model model) throws ParseException {
        List<BankingReplenishment> list = userService.userList(user);
        Date dateFromParse = new SimpleDateFormat("yyyy-MM-dd").parse(dateForm);
        Date dateToParse = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
        List<BankingReplenishment> sorted = list.stream()
                .filter(x -> x.getTransactionDate().after(dateFromParse) && x.getTransactionDate().before(dateToParse))
                .collect(Collectors.toList());
        List<OperationDTO> operation = userService.operationList(sorted);
        model.addAttribute("operationDTOList", operation);
        return "useroperationinfo";
    }
}