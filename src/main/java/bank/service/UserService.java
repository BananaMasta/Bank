package bank.service;

import bank.dto.OperationDTO;
import bank.models.BankingReplenishment;
import bank.models.Card;
import bank.models.Role;
import bank.models.User;
import bank.repository.BankReplenishmentRepository;
import bank.repository.CardRepository;
import bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankReplenishmentRepository bankReplenishmentRepository;

    @Autowired
    CardRepository cardRepository;


    public boolean registrationUser(User user) {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.User);
        user.setUserRole(roleList);
        user.setActive(true);
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        } else {
            System.out.println(user);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<BankingReplenishment> userList(User user) {
        List<Card> card = cardRepository.findCardsByUser(user);
        List<BankingReplenishment> bankReplList = new ArrayList<>();
        for (int i = 0; i < card.size(); i++) {
            Card card1 = card.get(i);
            List<BankingReplenishment> a = bankReplenishmentRepository.findByCardTo(card1);
            for (int j = 0; j < a.size(); j++) {
                bankReplList.add(a.get(j));
            }
            List<BankingReplenishment> cardFrom = bankReplenishmentRepository.findByCardFrom(card1);
            for (int j = 0; j < cardFrom.size(); j++) {
                bankReplList.add(cardFrom.get(j));
            }
        }
        return bankReplList;
    }

    public List<OperationDTO> operationList(List<BankingReplenishment> sortedList) {
        List<OperationDTO> operation = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            BankingReplenishment bankRepl = sortedList.get(i);
            OperationDTO opDTO = new OperationDTO();
            opDTO.setCardNumberFrom(bankRepl.getCardFrom().getCardNumber());
            opDTO.setCardNumberTo(bankRepl.getCardTo().getCardNumber());
            opDTO.setOperationSum(bankRepl.getTransactionSum());
            opDTO.setOperationDate(bankRepl.getTransactionDate());
            opDTO.setCurrency(bankRepl.getCardTo().getCurrency());
            operation.add(opDTO);
        }
        return operation;
    }

}
