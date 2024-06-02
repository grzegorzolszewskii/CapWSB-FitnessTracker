package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTrainingById(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return A list of trainings.
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves all trainings for a specific user based on user ID.
     *
     * @param id id of the user whose trainings are in the list
     * @return A list of all trainings for the specified user
     */
    List<Training> getTrainingsByUserId(Long id);

    /**
     * Retrieves trainings that ended before given date.
     *
     * @param trainingDate the date to compare the input date
     * @return A list of trainings that ended before given date
     */
    List<Training> getTrainingsOlderThan(Date trainingDate);

    /**
     * Retrieves all trainings with given activity type.
     *
     * @param activity the activity type of the trainings on the list
     * @return A list of trainings with given activity type
     */
    List<Training> getTrainingsByActivity(ActivityType activity);

}
