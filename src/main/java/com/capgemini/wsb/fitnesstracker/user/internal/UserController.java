package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return userMapper.toDto(createdUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> userUpdated = userService.getUserById(id);
        User userForUpdate = userMapper.toEntity(userDto);
        userService.updateUser(id, userForUpdate);
        return userMapper.toDto(userUpdated.get());
        // do poprawy
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            return userMapper.toDto(userOptional.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/email")
    public UserDto getUserByEmail(@RequestParam String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);
        if (userOptional.isPresent()) {
            return userMapper.toDto(userOptional.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @GetMapping("/older_than")
    public List<UserDto> findUsersOlderThan(@RequestParam LocalDate chosenDate){
        return userService.findUsersOlderThan(chosenDate)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/email_part")
    public List<UserDto> findByEmailPartIgnoreCase(@RequestParam String emailPart){
        return userService.findByEmailPartIgnoreCase(emailPart)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}