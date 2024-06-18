package me.qunqun.doctor.entity.reps;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;


@Data
public class ModelResponse {
    // Getters and Setters
    private String model;
    private ZonedDateTime createdAt;
    private String response;
    private boolean done;
    private String doneReason;
    private List<Integer> context;
    private long totalDuration;
    private long loadDuration;
    private int promptEvalCount;
    private long promptEvalDuration;
    private int evalCount;
    private long evalDuration;
}
