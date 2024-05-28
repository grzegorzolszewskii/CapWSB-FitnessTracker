package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return userMapper.toDto(createdUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUser(id);
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> userUpdated = userService.getUser(id);
        User userForUpdate = userMapper.toEntity(userDto);
        userService.updateUser(id, userForUpdate);
        return userMapper.toDto(userUpdated.get());
        // do poprawy
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            return userMapper.toDto(userOptional.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }






}