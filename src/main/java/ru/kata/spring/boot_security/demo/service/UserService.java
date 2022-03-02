package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    void deleteUser(Long id);
    void saveUser(User user);
    void updateUser(User user, String password);
    void setUserRole(String email, String nameRole);
}
