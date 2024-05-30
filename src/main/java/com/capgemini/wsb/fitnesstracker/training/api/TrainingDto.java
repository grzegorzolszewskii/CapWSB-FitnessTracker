package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record TrainingDto(Long id, User user,
                          @JsonFormat(pattern = "yyyy-MM-dd") Date startTime,
                          @JsonFormat(pattern = "yyyy-MM-dd") Date endTime,
                          ActivityType activityType,
                          double distance, double averageSpeed) {

}
