package com.studyplatform.server.services;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.Task;
import com.studyplatform.server.models.User;
import com.studyplatform.server.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByGroupId(Long groupId){
        Group group = groupService.getGroupById(groupId);
        return taskRepository.findByGroup(group);
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public boolean canEditTask(Task task, User user) {
        Group group = task.getGroup();
        boolean isOwner = group.getCreatedBy().equals(user.getEmail());
        boolean isMember = group.getUsers().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));

        return isOwner || isMember;
    }


    public Task createTask(String title, String description, LocalDateTime deadline, Group group, User user){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus("Pending");
        task.setDeadline(deadline);
        task.setGroup(group);
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, String title, String description, String status, LocalDateTime deadline){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(title);
        task.setStatus(status);
        task.setDescription(description);
        task.setDeadline(deadline);

        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }


    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)) throw new RuntimeException("Task not found");
        taskRepository.deleteById(id);
    }
}
