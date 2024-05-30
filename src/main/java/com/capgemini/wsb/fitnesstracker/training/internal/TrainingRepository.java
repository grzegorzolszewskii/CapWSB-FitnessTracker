package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface TrainingRepository extends JpaRepository<Training, Long> {

    default List<Training> getTrainingByUser(Long id){
        return null;
    };

    default List<Training> getTrainingsOlderThan(Date trainingDate){
        return null;
    }

    default List<Training> getTrainingByActivity(ActivityType activity){
        return null;
    }


}
