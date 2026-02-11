package study.example.service;

import study.example.domain.User;

import java.util.Optional;

public interface UserService {

    void join(User user);

    Optional<User> findUser(Long userId);
}
