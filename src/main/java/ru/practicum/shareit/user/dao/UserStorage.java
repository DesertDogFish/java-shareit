package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    Optional<User> get(Integer id);

    List<User> get();

    User save(User user);

    Optional<User> delete(Integer id);

    boolean emailExists(String email);

    boolean exists(Integer id);
}
