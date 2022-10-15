package bank.util;

import bank.models.Card;
import bank.models.Currency;
import bank.models.User;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardUtil {

    static User user;
    Card card;
    List<Card> usersCard = new ArrayList<>();

//    public static void main(String[] args) {
//        System.out.println(rndCVSCode() + " " + dateExpired());
//        for (int i = 0; i < cardList().size(); i++) {
//            System.out.println(cardList().get(i).toString());
//        }
//    }

    public static String cardNumberGener() {
        String cardNo = "";
        for (int i = 0; i < 4; i++) {
            cardNo = cardNo + (int)((Math.random() * 8888)+1111) + " ";
        }
        return cardNo;
    }

    static boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public static String rndCVSCode() {
        String num1 = "";
        for (int i = 0; i < 3; i++) {
            num1 = num1 + (int) ((Math.random() * 9) + 1);
        }
        return num1;
    }

    public static String dateExpired() {
        Date date;
        DateFormat df = new SimpleDateFormat("MM/yy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3);
        date = cal.getTime();
        return df.format(date);
    }

//    public static List<Card> cardList() {
//
//        String cardNum = "";
//        cardNum = cardNumberGener();
//        Card card = new Card(cardNum, user, dateExpired(), rndCVSCode(), Currency.RUB, new BigDecimal("0.00"));
//        usersCard.add(card);
//        return usersCard;
//    }
}
