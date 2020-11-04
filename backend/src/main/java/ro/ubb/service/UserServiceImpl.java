package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.ubb.error.SpecialError;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;
import ro.ubb.validator.UserValidator;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;

    @Override
    public boolean validUserCredentials(User user) {
        return userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Override
    public boolean register(User user) {
        if (!userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            if (!userValidator.validatePassword(user.getPassword())) {
                userRepository.save(user);
            }
            throw new SpecialError("Not a valid password", HttpStatus.FORBIDDEN);
        }
        throw new SpecialError("User with this email: " + user.getEmail() + " already registered", HttpStatus.FORBIDDEN);
    }

    @Override
    public Integer login(User user) {
        return userRepository.getIdOfUserWithCredentials(user.getEmail(), user.getPassword());
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
