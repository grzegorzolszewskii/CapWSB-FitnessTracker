package com.capgemini.wsb.fitnesstracker.training.api;

/**
 * Interface (API) for modifying operations on {@link Training} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface TrainingService {

    Training createTraining(Training training);
    Training updateTraining(Long id, Training training);
    Training updateTrainingDistance(Long id, double distance);
    void deleteTraining(Long id);

}
