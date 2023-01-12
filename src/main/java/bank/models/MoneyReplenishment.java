package bank.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class MoneyReplenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private BigDecimal sumReplenishment;
    @Enumerated
    private Currency currency;

    public MoneyReplenishment() {

    }

    public MoneyReplenishment(Card card, BigDecimal sumReplenishment, Currency currency) {
        this.card = card;
        this.sumReplenishment = sumReplenishment;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long operationId) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getSumReplenishment() {
        return sumReplenishment;
    }

    public void setSumReplenishment(BigDecimal sumReplenishment) {
        this.sumReplenishment = sumReplenishment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
