package com.studyplatform.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/tasks")
public class TaskController {

    @PostMapping
    public String createTask(@PathVariable Long groupId){
        return "Task created in group " + groupId;
    }

    @GetMapping
    public String getTask(@PathVariable Long groupId){
        return "Tasks for group " + groupId;
    }

}
