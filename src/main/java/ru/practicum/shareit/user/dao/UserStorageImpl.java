package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserStorageImpl implements UserStorage {
    private Integer counter = 1;
    private final Map<Integer, User> repository = new HashMap<>();

    @Override
    public Optional<User> get(Integer id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<User> get() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(counter++);
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> delete(Integer id) {
        return Optional.ofNullable(repository.remove(id));
    }

    @Override
    public boolean exists(Integer id) {
        return repository.containsKey(id);
    }

    @Override
    public boolean emailExists(String email) {
        return repository.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
