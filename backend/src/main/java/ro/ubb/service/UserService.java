package ro.ubb.service;

import ro.ubb.model.User;

public interface UserService {

    boolean validUserCredentials(User user);

    Integer login(User user);

    boolean existsById(int userId);
}
