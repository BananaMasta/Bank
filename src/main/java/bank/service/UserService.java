package bank.service;

import bank.models.Role;
import bank.models.User;
import bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
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
}
