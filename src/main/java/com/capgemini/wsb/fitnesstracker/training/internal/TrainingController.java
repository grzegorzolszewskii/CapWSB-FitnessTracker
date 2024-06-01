package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @PostMapping("/add")
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training createdTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(createdTraining);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTraining(@PathVariable Long id) {
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        trainingService.deleteTraining(id);
    }

    @PutMapping("/update/{id}")
    public TrainingDto updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Optional<Training> trainingUpdated = trainingService.getTrainingById(id);
        Training trainingForUpdate = trainingMapper.toEntity(trainingDto);
        trainingService.updateTraining(id, trainingForUpdate);
        return trainingMapper.toDto(trainingUpdated.get());
        // do poprawy
    }

    @GetMapping("/{id}")
    public TrainingDto getTrainingById(@PathVariable Long id) {
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        if (trainingOptional.isPresent()) {
            return trainingMapper.toDto(trainingOptional.get());
        } else {
            throw new TrainingNotFoundException(id);
        }
    }

    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/older_than")
    public List<TrainingDto> getTrainingsOlderThan(Date chosenDate){
        return trainingService.getTrainingsOlderThan(chosenDate)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivity(ActivityType activity){
        return trainingService.getTrainingsByActivity(activity)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}