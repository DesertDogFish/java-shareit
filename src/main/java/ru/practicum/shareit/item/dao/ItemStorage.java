package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Optional<Item> get(Integer id);

    Item save(Item item);

    Optional<Item> delete(Integer id);

    boolean itemExists(Integer id);

    List<Item> getAll(String text);

    List<Item> getAll(Integer userId);
}
