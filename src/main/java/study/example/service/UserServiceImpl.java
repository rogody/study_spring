package study.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.example.domain.User;
import study.example.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(User user) {
        if(userRepository.findByName(user.getUserName()).isPresent())
            throw new IllegalArgumentException("동일한 이름의 user가 존재합니다");
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(Long userId) {
        return userRepository.findById(userId);
    }

    //임의로 마스터 계정으로 고정해서 사용, 추후 로그인 기능 완성 시 수정
    @Override
    public Long getCurrentId(){
        User master = userRepository.findByName("Master").orElseThrow(()->new IllegalArgumentException("Master 계정이 존재하지 않습니다."));
        return master.getUserId();
    }

}
