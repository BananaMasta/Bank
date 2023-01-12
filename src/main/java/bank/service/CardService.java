package bank.service;

import bank.dto.OperationDTO;
import bank.models.BankingReplenishment;
import bank.models.Card;
import bank.models.Periods;
import bank.models.User;
import bank.repository.BankReplenishmentRepository;
import bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    BankReplenishmentRepository bankingReplenishmentRepository;

    @Autowired
    UserService userService;

    @Autowired
    CardRepository cardRepository;


    public List<BankingReplenishment> listSumTransactions(User user, String periodsSum) {
        List<BankingReplenishment> userOperation = userService.userList(user);
        List<BankingReplenishment> operation = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Periods period = Periods.valueOf(periodsSum);
        if (period.equals(Periods.Five_Days)) {
            calendar.add(Calendar.DAY_OF_WEEK, -5);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())).collect(Collectors.toList());
        } else if (period.equals(Periods.One_Week)) {
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())).collect(Collectors.toList());
        } else if (period.equals(Periods.Two_Weeks)) {
            calendar.add(Calendar.DAY_OF_WEEK, -14);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())).collect(Collectors.toList());
        } else if (period.equals(Periods.Month)) {
            calendar.add(Calendar.DAY_OF_WEEK, -30);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())).collect(Collectors.toList());
        }
        return operation;
    }

    public List<BankingReplenishment> cardList(User user, String cardNumber, String periods) {
        List<BankingReplenishment> userOperation = userService.userList(user);
        List<BankingReplenishment> operation = new ArrayList<>();
        Periods period = Periods.valueOf(periods);
        Calendar calendar = Calendar.getInstance();
        if (period.equals(Periods.Five_Days)) {
            calendar.add(Calendar.DAY_OF_WEEK, -5);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())
                            && x.getCardFrom().getCardNumber().equals(cardNumber)).collect(Collectors.toList());
        } else if (period.equals(Periods.One_Week)) {
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())
                            && x.getCardFrom().getCardNumber().equals(cardNumber)).collect(Collectors.toList());
        } else if (period.equals(Periods.Two_Weeks)) {
            calendar.add(Calendar.DAY_OF_WEEK, -14);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())
                            && x.getCardFrom().getCardNumber().equals(cardNumber)).collect(Collectors.toList());
        } else if (period.equals(Periods.Month)) {
            calendar.add(Calendar.DAY_OF_WEEK, -30);
            Date date = calendar.getTime();
            operation = userOperation.stream()
                    .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate().before(new Date())
                            && x.getCardFrom().getCardNumber().equals(cardNumber)).collect(Collectors.toList());
        }
        return operation;
    }

//    public List<BankingReplenishment> spendPerDay(User user, String day, String timeFrom, String timeTo) throws ParseException {
//        List<BankingReplenishment> userOperation = userService.userList(user);
//        List<BankingReplenishment> operation;
//        Date dateParse = new SimpleDateFormat("yyyy-MM-dd").parse(day);
//        Date timeParseFrom = new SimpleDateFormat("hh:mm").parse(timeFrom);
//        Date timeParseTo = new SimpleDateFormat("hh:mm").parse(timeTo);
//        operation = userOperation.stream().filter(x -> x.getTransactionDate().equals(dateParse)
//                && x.getTransactionDate().after(timeParseFrom) && x.getTransactionDate().before(timeParseTo)).collect(Collectors.toList());
//        return operation;
//    }

    public List<BankingReplenishment> allUserCards(User user) {
        List<Card> userCardList = cardRepository.findCardsByUser(user);
        List<BankingReplenishment> userTransactionsList = userService.userList(user);
        List<BankingReplenishment> transactionsList = new ArrayList<>();
        for (int i = 0; i < userTransactionsList.size(); i++) {
            if (userCardList.contains(userTransactionsList.get(i).getCardTo())) {
                transactionsList.add(userTransactionsList.get(i));
            }
        }
        transactionsList.sort(new Comparator<BankingReplenishment>() {
            @Override
            public int compare(BankingReplenishment o1, BankingReplenishment o2) {
                return o1.getTransactionDate().compareTo(o2.getTransactionDate());
            }
        });
        return transactionsList;
    }

    public String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        return dateFormat.format(date);
    }

    public Map<String, BigDecimal> spendList(User user) {
        List<BankingReplenishment> list = allUserCards(user);
        Map<String, BigDecimal> transactionMap = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (transactionMap.containsKey(dateToString(list.get(i).getTransactionDate()))) {
                BigDecimal transactionSum = transactionMap.get(dateToString(list.get(i).getTransactionDate()));
                transactionMap.replace(dateToString(list.get(i).getTransactionDate()), transactionSum.add(list.get(i).getTransactionSum()));
            } else {
                transactionMap.put(dateToString(list.get(i).getTransactionDate()), list.get(i).getTransactionSum());
            }
        }
        return transactionMap;
    }

    public Map<String, BigDecimal> spendListPeriodSort(User user, Calendar calendar) {
        List<BankingReplenishment> userOperation = allUserCards(user);
        Map<String, BigDecimal> transactionMap = new LinkedHashMap<>();
        Date date = calendar.getTime();
        List<BankingReplenishment> operation = userOperation.stream()
                .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate()
                        .before(new Date())).collect(Collectors.toList());
        for (int i = 0; i < operation.size(); i++) {
            if (transactionMap.containsKey(dateToString(operation.get(i).getTransactionDate()))) {
                BigDecimal transactionSum = transactionMap.get(dateToString(operation.get(i).getTransactionDate()));
                transactionMap.replace(dateToString(operation.get(i).getTransactionDate()), transactionSum.add(operation.get(i).getTransactionSum()));
            } else {
                transactionMap.put(dateToString(operation.get(i).getTransactionDate()), operation.get(i).getTransactionSum());
            }
        }
        return transactionMap;
    }

    public Map<String, BigDecimal> mapSumTransactions(User user, String periodsSum) {
        Map<String, BigDecimal> sumTransactions = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Periods period = Periods.valueOf(periodsSum);
        switch (period) {
            case Five_Days:
                calendar.add(Calendar.DAY_OF_WEEK, -5);
                sumTransactions = spendListPeriodSort(user, calendar);
                break;
            case One_Week:
                calendar.add(Calendar.DAY_OF_WEEK, -7);
                sumTransactions = spendListPeriodSort(user, calendar);
                break;
            case Two_Weeks:
                calendar.add(Calendar.DAY_OF_WEEK, -14);
                sumTransactions = spendListPeriodSort(user, calendar);
                break;
            case Month:
                calendar.add(Calendar.DAY_OF_WEEK, -30);
                sumTransactions = spendListPeriodSort(user, calendar);
                break;
        }
        return sumTransactions;
    }

    public Map<String, BigDecimal> middleSpendList(User user, Calendar calendar) {
        List<BankingReplenishment> userOperation = allUserCards(user);
        Map<String, BigDecimal> transactionMap = new LinkedHashMap<>();
        Date date = calendar.getTime();
        BigDecimal transactionSum = BigDecimal.valueOf(0);
        List<BankingReplenishment> operation = userOperation.stream()
                .filter(x -> x.getTransactionDate().after(date) && x.getTransactionDate()
                        .before(new Date())).collect(Collectors.toList());
        for (int i = 0; i < operation.size(); i++) {
            if (transactionMap.containsKey(dateToString(operation.get(i).getTransactionDate()))) {
                transactionSum = transactionMap.get(dateToString(operation.get(i).getTransactionDate()));
                transactionMap.replace(dateToString(operation.get(i).getTransactionDate()), (transactionSum.add(operation.get(i).getTransactionSum())));
            } else {
                transactionMap.put(dateToString(operation.get(i).getTransactionDate()), operation.get(i).getTransactionSum());
            }
        }
//        .divide(BigDecimal.valueOf(operation.size()), 2, RoundingMode.HALF_UP)
        System.out.println(transactionSum);
        return transactionMap;
    }

    public Map<String, BigDecimal> middleTransactions(User user, String periodsSum) {
        Map<String, BigDecimal> middleTransactions = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Periods period = Periods.valueOf(periodsSum);
        switch (period) {
            case Five_Days:
                calendar.add(Calendar.DAY_OF_WEEK, -5);
                middleTransactions = middleSpendList(user, calendar);
                break;
            case One_Week:
                calendar.add(Calendar.DAY_OF_WEEK, -7);
                middleTransactions = middleSpendList(user, calendar);
                break;
            case Two_Weeks:
                calendar.add(Calendar.DAY_OF_WEEK, -14);
                middleTransactions = middleSpendList(user, calendar);
                break;
            case Month:
                calendar.add(Calendar.DAY_OF_WEEK, -30);
                middleTransactions = middleSpendList(user, calendar);
                break;
        }
        return middleTransactions;
    }
}
