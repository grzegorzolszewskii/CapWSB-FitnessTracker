package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserFirstName(Long id, String firstName){
        User user = userRepository.findById(id).get();
        user.setFirstName(firstName);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserSimpleDto> findAllSimpleUsers() {
        return userRepository.findAllSimpleUsers();
    }


    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<User> findUsersOlderThan(LocalDate chosenDate) {
        return userRepository.findUsersOlderThan(chosenDate);
    }

    @Override
    public List<User> findByEmailPartIgnoreCase(String emailPart) {
        return userRepository.findByEmailPartIgnoreCase(emailPart);
    }
}