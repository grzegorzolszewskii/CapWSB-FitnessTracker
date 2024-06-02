package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> getUserByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * SQL Query searching all users and simple data about them
     *
     * @return List of {@link UserSimpleDto} containing first and last names of all users
     */
    @Query("SELECT new com.capgemini.wsb.fitnesstracker.user.internal.UserSimpleDto(u.firstName, u.lastName) FROM User u")
    List<UserSimpleDto> findAllSimpleUsers();

    /**
     * SQL Query searching for users whose birthdate is before the given date.
     *
     * @param chosenDate The date to compare
     * @return List of {@link User} whose birthdate is before the given date.
     */
    @Query("SELECT u FROM User u WHERE u.birthdate < :chosenDate")
    List<User> findUsersOlderThan(@Param("chosenDate") LocalDate chosenDate);

    /**
     * SQL Query that searches for users whose email contains the specified text, case-insensitive.
     *
     * @param email The text to compare to emails
     * @return List of {@link User} whose email contains the specified text
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailPartIgnoreCase(@Param("email") String email);

}
