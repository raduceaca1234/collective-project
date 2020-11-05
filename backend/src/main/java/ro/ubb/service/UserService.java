package ro.ubb.service;

import ro.ubb.model.User;

public interface UserService {

    boolean validUserCredentials(User user);

    boolean register(User user);

    Integer login(User user);
}
