package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public Training createTraining(Training training) {
        if (training.getUser().getId() == null) {
            throw new IllegalArgumentException("User id must not be null");
        }

        User user = userRepository.findById(training.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        training.setUser(user);

        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long trainingId, Training trainingForUpdate) {
        Optional<Training> existingTrainingOptional = trainingRepository.findById(trainingId);
        if (existingTrainingOptional.isPresent()) {
            Training existingTraining = existingTrainingOptional.get();
            existingTraining.setUser(trainingForUpdate.getUser());
            existingTraining.setStartTime(trainingForUpdate.getStartTime());
            existingTraining.setEndTime(trainingForUpdate.getEndTime());
            existingTraining.setActivityType(trainingForUpdate.getActivityType());
            existingTraining.setDistance(trainingForUpdate.getDistance());
            existingTraining.setAverageSpeed(trainingForUpdate.getAverageSpeed());
            return trainingRepository.save(existingTraining);
        } else {
            // do poprawy
            return null;
        }
    }

    @Override
    public Training updateTrainingDistance(Long id, double distance) {
        Training training = trainingRepository.findById(id).get();
        training.setDistance(distance);
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public Optional<Training> getTrainingById(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long id) {
        return trainingRepository.getTrainingsByUserId(id);
    }

    @Override
    public List<Training> getTrainingsOlderThan(Date trainingDate) {
        return trainingRepository.getTrainingsOlderThan(trainingDate);
    }

    @Override
    public List<Training> getTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.getTrainingsByActivity(activityType);
    }
}
