package bank.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.time.LocalDateTime;
@Entity
public class BankingTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userCardId;
    private long replenishment;
    private String nameTransactionFromUser;
    private String nameTransactionToUser;
    private BigInteger userMoneyInAccount;
    private BigInteger moneyToUser;
    private LocalDateTime initialDateTime;
    private LocalDateTime completedDateTime;
    private double amount;

    public BankingTransactions() {
    }

    public BankingTransactions(long userCardId, String nameTransactionFromUser, String nameTransactionToUser,
                               BigInteger userMoneyInAccount, BigInteger moneyToUser, LocalDateTime initialDateTime,
                               LocalDateTime completedDateTime, double amount) {
        this.userCardId = userCardId;
        this.nameTransactionFromUser = nameTransactionFromUser;
        this.nameTransactionToUser = nameTransactionToUser;
        this.userMoneyInAccount = userMoneyInAccount;
        this.moneyToUser = moneyToUser;
        this.initialDateTime = initialDateTime;
        this.completedDateTime = completedDateTime;
        this.amount = amount;
    }

    public BankingTransactions(long userCardId, long replenishment, BigInteger userMoneyInAccount, LocalDateTime initialDateTime, double amount) {
        this.userCardId = userCardId;
        this.replenishment = replenishment;
        this.userMoneyInAccount = userMoneyInAccount;
        this.initialDateTime = initialDateTime;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(long userCardId) {
        this.userCardId = userCardId;
    }

    public long getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(long replenishment) {
        this.replenishment = replenishment;
    }

    public String getNameTransactionFromUser() {
        return nameTransactionFromUser;
    }

    public void setNameTransactionFromUser(String nameTransactionFromUser) {
        this.nameTransactionFromUser = nameTransactionFromUser;
    }

    public String getNameTransactionToUser() {
        return nameTransactionToUser;
    }

    public void setNameTransactionToUser(String nameTransactionToUser) {
        this.nameTransactionToUser = nameTransactionToUser;
    }

    public BigInteger getUserMoneyInAccount() {
        return userMoneyInAccount;
    }

    public void setUserMoneyInAccount(BigInteger userMoneyInAccount) {
        this.userMoneyInAccount = userMoneyInAccount;
    }

    public BigInteger getMoneyToUser() {
        return moneyToUser;
    }

    public void setMoneyToUser(BigInteger moneyToUser) {
        this.moneyToUser = moneyToUser;
    }

    public LocalDateTime getInitialDateTime() {
        return initialDateTime;
    }

    public void setInitialDateTime(LocalDateTime initialDateTime) {
        this.initialDateTime = initialDateTime;
    }

    public LocalDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return getId() + " " + getNameTransactionFromUser() + " " + getUserMoneyInAccount() + " " + getNameTransactionToUser() + " "
                + getMoneyToUser() + " " + getInitialDateTime();
    }
}
