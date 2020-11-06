package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public boolean validUserCredentials(User user) {
        return userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Override
    public Integer login(User user) {
        return userRepository.getIdOfUserWithCredentials(user.getEmail(), user.getPassword());
    }

    @Override
    public boolean existsById(int userId) {
        return userRepository.existsById(userId);
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
