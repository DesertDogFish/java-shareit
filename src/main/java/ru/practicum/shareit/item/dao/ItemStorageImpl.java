package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private Integer counter = 1;
    private final Map<Integer, Item> repository = new HashMap<>();

    @Override
    public Optional<Item> get(Integer id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(counter++);
        }
        repository.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> delete(Integer id) {
        return Optional.ofNullable(repository.remove(id));
    }

    @Override
    public List<Item> getAll(String text) {
        return repository.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getAll(Integer userId) {
        return repository.values().stream()
                .filter(item -> Objects.equals(item.getOwnerId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean itemExists(Integer id) {
        return repository.containsKey(id);
    }
}