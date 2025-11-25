package com.studyplatform.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @PostMapping
    public String createGroup(){
        return "Group created";
    }

    @GetMapping
    public String getAllGroups(){
        return "All Groups list";
    }

    @GetMapping("/{id}")
    public String getGroup(@PathVariable Long id){
        return "Group detail: " + id;
    }
}
