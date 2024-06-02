package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
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
    public User updateUser(Long userId, User userForUpdate) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setFirstName(userForUpdate.getFirstName());
            existingUser.setLastName(userForUpdate.getLastName());
            existingUser.setBirthdate(userForUpdate.getBirthdate());
            existingUser.setEmail(userForUpdate.getEmail());
            return userRepository.save(existingUser);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public User updateUserFirstName(Long id, String firstName) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            user.setFirstName(firstName);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public Optional<User> getUserById(final Long userId) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            return userRepository.findById(userId);
        } else {
            throw new UserNotFoundException(userId);
        }
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