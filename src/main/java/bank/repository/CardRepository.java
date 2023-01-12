package bank.repository;

import bank.models.Card;
import bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByUser(User user);

    Card findByCardNumber(String cardNumber);

}
