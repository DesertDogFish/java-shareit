package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto get(Integer id) {
        User user = userStorage.get(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> get() {
        return userStorage.get().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new ConflictException("This email is already taken");
        }
        return UserMapper.toUserDto(userStorage.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto delete(Integer id) {
        User user = userStorage.delete(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(UserDto userDto, Integer id) {
        User newUser = userStorage.get(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (userDto.getName() != null) {
            newUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !newUser.getEmail().equals(userDto.getEmail())) {
            if (emailExists(userDto.getEmail())) {
                throw new ConflictException("This email is already taken");
            }
            newUser.setEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(userStorage.save(newUser));
    }

    @Override
    public boolean exists(Integer id) {
        return userStorage.exists(id);
    }

    private boolean emailExists(String email) {
        return userStorage.emailExists(email);
    }
}
