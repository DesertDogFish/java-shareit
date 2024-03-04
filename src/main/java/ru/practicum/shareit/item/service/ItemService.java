package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto get(Integer id);

    List<ItemDto> getAll(Integer userId);

    ItemDto save(ItemDto itemDto, Integer userId);

    ItemDto delete(Integer id);

    ItemDto patch(ItemDto itemDto, Integer itemId, Integer userId);

    boolean exists(Integer id);

    List<ItemDto> search(String text, Integer userId);
}
