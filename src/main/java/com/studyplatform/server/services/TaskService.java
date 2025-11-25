package com.studyplatform.server.services;

import com.studyplatform.server.models.Task;
import com.studyplatform.server.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByGroupId(Long groupId){
        return taskRepository.findByGroupId(groupId);
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Task createTask(String title, String description, Long groupId){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setGroupId(groupId);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, String title, String description, String status){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(title);
        task.setStatus(status);
        task.setDescription(description);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
