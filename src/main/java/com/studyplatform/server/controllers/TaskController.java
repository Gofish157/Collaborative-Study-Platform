package com.studyplatform.server.controllers;

import com.studyplatform.server.dto.TaskDTO;
import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.Task;
import com.studyplatform.server.models.User;
import com.studyplatform.server.services.GroupService;
import com.studyplatform.server.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.studyplatform.server.security.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups/{groupId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<TaskDTO> getTasks(@PathVariable Long groupId){
        return taskService.getTasksByGroupId(groupId).stream()
                .map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline(), task.getGroup().getId(), task.getUser().getId(), task.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTask(@PathVariable Long groupId, @PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline(), task.getGroup().getId(), task.getUser().getId(), task.getCreatedAt());
    }

    @PostMapping
    public TaskDTO createTask(@PathVariable Long groupId, @RequestBody TaskDTO taskDTO, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Group group = groupService.getGroupById(groupId);
        User user = userDetails.getUser();

        if (!isMember(group, userDetails.getUser())) {
            throw new RuntimeException("Unauthorized to create tasks in this group");
        }

        Task task = taskService.createTask(taskDTO.title(), taskDTO.description(), taskDTO.deadline(), group, user);
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline(), task.getGroup().getId(),task.getUser().getId(), task.getCreatedAt());
    }

    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable Long groupId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Task existingTask = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        if (!taskService.canEditTask(existingTask, userDetails.getUser())) {
            throw new RuntimeException("Unauthorized to update tasks from this group");
        }

        Task task = taskService.updateTask(taskId, taskDTO.title(), taskDTO.description(), taskDTO.status(), taskDTO.deadline());

        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline(), task.getGroup().getId(),task.getUser().getId(), task.getCreatedAt());
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long groupId, @PathVariable Long taskId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!taskService.canEditTask(task, userDetails.getUser())) {
            throw new RuntimeException("Unauthorized to delete tasks from this group");
        }

        taskService.deleteTask(taskId);
        return "Task deleted: " + taskId;
    }

    @GetMapping("/my-tasks")
    public List<TaskDTO> getMyTasks(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return taskService.getTasksByUser(user).stream()
                .map(task -> new TaskDTO(
                        task.getId(), task.getTitle(), task.getDescription(),
                        task.getStatus(), task.getDeadline(),
                        task.getGroup().getId(), task.getUser().getId(), task.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }



    private boolean isMember(Group group, User user) {
        return group.getUsers().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));
    }


}
