package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public ItemDto get(Integer id) {
        Item item = itemStorage.get(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAll(Integer userId) {
        validateUserExists(userId);
        return itemStorage.getAll(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto save(ItemDto itemDto, Integer userId) {
        validateUserExists(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwnerId(userId);
        return ItemMapper.toItemDto(itemStorage.save(item));
    }

    @Override
    public ItemDto delete(Integer id) {
        Item item = itemStorage.delete(id).orElseThrow(() -> new NotFoundException("Item not found"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto patch(ItemDto itemDto, Integer itemId, Integer userId) {
        validateUserExists(userId);
        Item newItem = itemStorage.get(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        if (!newItem.getOwnerId().equals(userId)) {
            throw new NotFoundException("Item " + itemId + " does not belong to user " + userId);
        }
        if (itemDto.getName() != null) {
            newItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            newItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            newItem.setAvailable(itemDto.getAvailable());
        }
        return ItemMapper.toItemDto(itemStorage.save(newItem));
    }

    @Override
    public List<ItemDto> search(String text, Integer userId) {
        validateUserExists(userId);
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return itemStorage.getAll(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(Integer id) {
        return itemStorage.itemExists(id);
    }

    private void validateUserExists(Integer userId) {
        if (!userService.exists(userId)) {
            throw new NotFoundException("User not found");
        }
    }
}
