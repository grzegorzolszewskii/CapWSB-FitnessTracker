package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT t FROM Training t WHERE t.user.id = :id")
    List<Training> getTrainingsByUserId(@Param("id") long id);

    @Query("SELECT t FROM Training t WHERE t.endTime < :chosenDate")
    List<Training> getTrainingsOlderThan(@Param("chosenDate") Date chosenDate);

    @Query("SELECT t FROM Training t WHERE t.activityType = :activityType")
    List<Training> getTrainingsByActivity(@Param("activityType") ActivityType activityType);
}
