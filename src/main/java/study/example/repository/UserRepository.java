package study.example.repository;

import study.example.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByName(String userName);

    List<User> findAll();

    void clearStore();
}
