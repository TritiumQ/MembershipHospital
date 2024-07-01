package me.qunqun.doctor.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskStatusService {
    private final ConcurrentHashMap<String, TaskStatus> taskStatusMap = new ConcurrentHashMap<>();

    public void updateTaskStatus(String taskId, String status, String result) {
        taskStatusMap.put(taskId, new TaskStatus(status, result));
    }

    public TaskStatus getTaskStatus(String taskId) {
        return taskStatusMap.get(taskId);
    }

    @Getter
    public static class TaskStatus {
        private String status;
        private String result;

        public TaskStatus(String status, String result) {
            this.status = status;
            this.result = result;
        }

    }
}
