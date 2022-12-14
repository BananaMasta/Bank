package bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cardId;
    private String cardNumber;
    @ManyToOne
    private User user;
    private Date dateExpired;
    private String ccvCode;
    @Enumerated
    private Currency currency;
    private BigDecimal userMoney;

    public Card() {
    }

    public Card(String cardNumber, User user, Date dateExpired, String ccvCode, Currency currency, BigDecimal userMoney) {
        this.cardNumber = cardNumber;
        this.user = user;
        this.dateExpired = dateExpired;
        this.ccvCode = ccvCode;
        this.currency = currency;
        this.userMoney = userMoney;
    }

    public Card(String ccvCode) {
        this.ccvCode = ccvCode;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public String getCcvCode() {
        return ccvCode;
    }

    public void setCcvCode(String ccvCode) {
        this.ccvCode = ccvCode;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public BigDecimal setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
        return userMoney;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", user=" + user +
                ", dateExpired=" + dateExpired +
                ", ccvCode='" + ccvCode + '\'' +
                ", currency=" + currency +
                ", userMoney=" + userMoney +
                '}';
    }
}
