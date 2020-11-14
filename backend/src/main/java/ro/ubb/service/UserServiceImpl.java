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
    public User login(User user) {
        return userRepository.getUserWithCredentials(user.getEmail(), user.getPassword());
    }
    @Override
    public boolean existsById(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean register(User user) {
        if (!userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            if (!userValidator.validatePassword(user.getPassword())) {
                userRepository.save(user);
                return true;
            }
            throw new SpecialError("Not a valid password", HttpStatus.FORBIDDEN);
        }
        throw new SpecialError("User with this email: " + user.getEmail() + " already registered", HttpStatus.FORBIDDEN);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator){
        this.userValidator = userValidator;
    }

}
