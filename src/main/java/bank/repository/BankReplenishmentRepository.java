package bank.repository;

import bank.models.BankingReplenishment;
import bank.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BankReplenishmentRepository extends JpaRepository<BankingReplenishment, Long> {

    List<BankingReplenishment> findByCardFrom(Card card);

    List<BankingReplenishment> findByCardTo(Card card);

}
