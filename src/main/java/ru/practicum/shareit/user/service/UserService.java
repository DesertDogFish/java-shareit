package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto get(Integer id);

    List<UserDto> get();

    UserDto save(UserDto userDto);

    UserDto delete(Integer id);

    UserDto update(UserDto userDto, Integer id);

    boolean exists(Integer id);
}
