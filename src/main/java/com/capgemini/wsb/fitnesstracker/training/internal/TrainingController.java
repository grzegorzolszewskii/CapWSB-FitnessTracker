package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingWithFullUserDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training createdTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(createdTraining);
    }

    @PutMapping("/{id}")
    public TrainingWithFullUserDto updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training updatedTraining = trainingService.updateTraining(id, training);
        return trainingMapper.toDtoWithFullUser(updatedTraining);
    }

    @PutMapping("/update_distance/{id}/{distance}")
    public TrainingDto updateDistance(@PathVariable Long id, @PathVariable double distance) {
        Training updatedTraining = trainingService.updateTrainingDistance(id, distance);
        return trainingMapper.toDto(updatedTraining);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTraining(@PathVariable Long id) {
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        trainingService.deleteTraining(id);
    }

    @GetMapping("/training_id/{id}")
    public TrainingWithFullUserDto getTrainingById(@PathVariable Long id) {
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        if (trainingOptional.isPresent()) {
            return trainingMapper.toDtoWithFullUser(trainingOptional.get());
        } else {
            throw new TrainingNotFoundException(id);
        }
    }

    @GetMapping("/{userId}")
    public List<TrainingWithFullUserDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDtoWithFullUser)
                .toList();
    }

    @GetMapping
    public List<TrainingWithFullUserDto> getAllTrainings() {
        return trainingService.findAllTrainings().stream()
                .map(trainingMapper::toDtoWithFullUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/finished/{chosenDate}")
    public List<TrainingWithFullUserDto> getTrainingsOlderThan(@PathVariable @DateTimeFormat Date chosenDate){
        return trainingService.getTrainingsOlderThan(chosenDate)
                .stream()
                .map(trainingMapper::toDtoWithFullUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/activityType")
    public List<TrainingWithFullUserDto> getTrainingsByActivity(ActivityType activityType){
        return trainingService.getTrainingsByActivity(activityType)
                .stream()
                .map(trainingMapper::toDtoWithFullUser)
                .toList();
    }
}