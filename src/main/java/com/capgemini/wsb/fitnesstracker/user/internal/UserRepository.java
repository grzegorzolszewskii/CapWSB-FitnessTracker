package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {

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

    @Query("SELECT u FROM User u WHERE u.birthdate < :chosenDate")
    List<User> findUsersOlderThan(@Param("chosenDate") LocalDate chosenDate);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailPartIgnoreCase(@Param("email") String email);

    /** default List<User> findUsersOlderThan(LocalDate chosenDate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(chosenDate))
                .toList();
    }

    default List<User> findByEmailPartIgnoreCase(String emailPart) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(emailPart.toLowerCase()))
                .collect(Collectors.toList());
    } **/

}
