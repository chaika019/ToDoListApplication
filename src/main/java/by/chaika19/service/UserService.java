package by.chaika19.service;

import by.chaika19.entity.User;
import by.chaika19.entity.UserRole;
import by.chaika19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findAllByRoleIn(Iterable<UserRole> roles) {
        return userRepository.findAllByRoleInOrderById(roles);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void updateRoll (int id, UserRole newRole) {
        userRepository.updateRoll(id, newRole);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        String email = authentication.getName();
        return  userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email = " + email + " not found"));
    }
}
