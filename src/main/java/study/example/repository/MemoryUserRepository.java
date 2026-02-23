package study.example.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import study.example.domain.User;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static Map<Long, User> userMem = new HashMap<>();
    private static Long sequence = 1L;

    @Override
    public User save(User user) {
        if(user.getUserId() == null)
            user.setUserId(sequence++);
        userMem.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(userMem.get(userId));
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userMem.values().stream().filter(user -> user.getUserName().equals(userName)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(userMem.values());
    }

    @Override
    public void clearStore() {
        userMem.clear();
    }

    @PostConstruct
    void temporaryAccount(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("Master");
        user.setPassword("1234");
        this.save(user);
    }
}
