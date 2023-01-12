package bank.models;

import bank.dto.OperationDTO;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class BankingReplenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "card_idFrom")
    private Card cardFrom;
    @ManyToOne
    @JoinColumn(name = "card_idTo")
    private Card cardTo;
    private BigDecimal transactionSum;
    private Date transactionDate;

    public BankingReplenishment(long id, Card cardTo, Card cardFrom, BigDecimal transactionSum, Date transactionDate) {
        this.id = id;
        this.cardTo = cardTo;
        this.cardFrom = cardFrom;
        this.transactionSum = transactionSum;
        this.transactionDate = transactionDate;
    }

    public BankingReplenishment(Card cardTo, Card cardFrom, BigDecimal transactionSum, Date transactionDate) {
        this.cardTo = cardTo;
        this.cardFrom = cardFrom;
        this.transactionSum = transactionSum;
        this.transactionDate = transactionDate;
    }

    public BankingReplenishment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTransactionSum() {
        return transactionSum;
    }

    public void setTransactionSum(BigDecimal transactionSum) {
        this.transactionSum = transactionSum;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Card getCardTo() {
        return cardTo;
    }

    public void setCardTo(Card cardTo) {
        this.cardTo = cardTo;
    }

    public Card getCardFrom() {
        return cardFrom;
    }

    public void setCardFrom(Card cardFrom) {
        this.cardFrom = cardFrom;
    }

}