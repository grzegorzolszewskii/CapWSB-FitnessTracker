package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.capgemini.wsb.fitnesstracker.user.internal.UserSimpleDto;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserById(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> findAllUsers();

    /**
     * Retrieves simple data about all users.
     *
     * @return A list containing simple data of all users.
     */
    List<UserSimpleDto> findAllSimpleUsers();

    /**
     * Retrieves all users older than given date.
     *
     * @param chosenDate The date to compare the birthdate of users.
     * @return A list of users older than given date.
     */
    List<User> findUsersOlderThan(LocalDate chosenDate);

    /**
     * Retrieves all users whose emails contains the given text, searching is not case-sensitive.
     *
     * @param emailPart Part of email text to be searched for.
     * @return A list of users whose email contains the given text
     */
    List<User> findByEmailPartIgnoreCase(String emailPart);
}
