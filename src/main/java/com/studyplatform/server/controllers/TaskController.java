package com.studyplatform.server.controllers;

import com.studyplatform.server.models.Task;
import com.studyplatform.server.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getGroupTasks(@PathVariable Long groupId){
        List<Task> tasks = taskService.getTasksByGroupId(groupId);
        return "Total Tasks in group " + groupId + ": " + tasks.size();
    }

    @PostMapping
    public String createTask(@PathVariable Long groupId){
        Task task = taskService.createTask("New Task", "Task Description", groupId);
        return "Task Created: " + task.getTitle() + " in Group ID: " + groupId;
    }


}
