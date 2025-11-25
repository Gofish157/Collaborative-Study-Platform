package com.studyplatform.server.controllers;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String getAllGroups(){
        List<Group> groups = groupService.getAllGroups();
        return "Total Groups: " + groups.size();
    }

    @GetMapping("/{id}")
    public String getGroup(@PathVariable Long id){
        return groupService.getGroupById(id)
                .map(group -> "Group Found: " + group.getName() + " - " + group.getDescription())
                .orElse("Group Not Found");
    }

    @PostMapping
    public String createGroup(){
        Group group = groupService.createGroup("Study Group", "A group for study sessions", 1L);
        return "Group Created: " + group.getName() + " - " + group.getDescription();
    }




}
