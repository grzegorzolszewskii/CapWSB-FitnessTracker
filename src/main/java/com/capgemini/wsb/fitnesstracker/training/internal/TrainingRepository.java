package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * SQL Query searching all trainings for given user.
     *
     * @param id ID of the user whose trainings are to be searched
     * @return List of {@link Training} for specified user
     */
    @Query("SELECT t FROM Training t WHERE t.user.id = :id")
    List<Training> getTrainingsByUserId(@Param("id") long id);

    /**
     * SQL Query searching trainings that ended before given date.
     *
     * @param chosenDate The date to compare the end time of the training
     * @return List of {@link Training} that ended before given date
     */
    @Query("SELECT t FROM Training t WHERE t.endTime < :chosenDate")
    List<Training> getTrainingsOlderThan(@Param("chosenDate") Date chosenDate);

    /**
     * SQL Query searching for trainings with specific activity type.
     *
     * @param activityType The type of activity
     * @return List of {@link Training} with specified activity type
     */
    @Query("SELECT t FROM Training t WHERE t.activityType = :activityType")
    List<Training> getTrainingsByActivity(@Param("activityType") ActivityType activityType);
}
