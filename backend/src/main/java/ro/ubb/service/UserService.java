package ro.ubb.service;

import ro.ubb.model.User;

public interface UserService {

    boolean validUserCredentials(User user);

    boolean register(User user);

    User login(User user);

    boolean existsById(int userId);

    User getById(int userId);
}
