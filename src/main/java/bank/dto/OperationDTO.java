package bank.dto;

import bank.models.Currency;
import bank.models.User;

import java.math.BigDecimal;
import java.util.Date;

public class OperationDTO {
    private long cardId;
    private String cardNumberFrom;
    private String cardNumberTo;
    private BigDecimal operationSum;
    private Date operationDate;

    private Currency currency;

    public OperationDTO() {
    }

    public OperationDTO(long cardId, String cardNumberFrom, String cardNumberTo, BigDecimal operationSum, Date operationDate, Currency currency) {
        this.cardId = cardId;
        this.cardNumberFrom = cardNumberFrom;
        this.cardNumberTo = cardNumberTo;
        this.operationSum = operationSum;
        this.operationDate = operationDate;
        this.currency = currency;
    }

    public OperationDTO(String cardNumberFrom, String cardNumberTo, BigDecimal operationSum, Date operationDate, Currency currency) {
        this.cardNumberFrom = cardNumberFrom;
        this.cardNumberTo = cardNumberTo;
        this.operationSum = operationSum;
        this.operationDate = operationDate;
        this.currency = currency;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumberFrom() {
        return cardNumberFrom;
    }

    public void setCardNumberFrom(String cardNumberFrom) {
        this.cardNumberFrom = cardNumberFrom;
    }

    public String getCardNumberTo() {
        return cardNumberTo;
    }

    public void setCardNumberTo(String cardNumberTo) {
        this.cardNumberTo = cardNumberTo;
    }

    public BigDecimal getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(BigDecimal operationSum) {
        this.operationSum = operationSum;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
