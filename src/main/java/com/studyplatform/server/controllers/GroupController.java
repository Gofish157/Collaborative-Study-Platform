package com.studyplatform.server.controllers;

import com.studyplatform.server.dto.GroupDTO;
import com.studyplatform.server.dto.GroupRequestDTO;
import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.User;
import com.studyplatform.server.security.UserDetailsImpl;
import com.studyplatform.server.services.GroupService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupDTO> getAllGroups(){
        return groupService.getAllGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getName(), group.getDescription(), group.getCreatedBy(), group.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GroupDTO getGroup(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return new GroupDTO(group.getId(), group.getName(), group.getDescription(), group.getCreatedBy(), group.getCreatedAt());
    }

    @PostMapping
    public GroupDTO createGroup(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String email = userDetails.getUsername(); // или userDetails.getUser().getEmail()
        Group group = groupService.createGroup(name, description, email);
        return GroupDTO.from(group);
    }


    @PutMapping("/{id}")
    public GroupDTO updateGroup(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String email = userDetails.getUsername();
        Group group = groupService.updateGroup(id, name, description, email);
        return GroupDTO.from(group);
    }


    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return "Group with ID " + id + " deleted successfully.";
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok("User added to group");
    }

    @PostMapping("/{groupId}/users")
    public ResponseEntity<Void> addUserToGroupByEmail(
            @PathVariable Long groupId,
            @RequestParam String email,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String ownerEmail = userDetails.getUsername();
        groupService.addUserToGroupByEmail(groupId, email, ownerEmail);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{groupId}/users")
    public ResponseEntity<Void> removeUserFromGroup(
            @PathVariable Long groupId,
            @RequestParam String email,
            Authentication auth
    ) {
        String ownerEmail = auth.getPrincipal().toString();
        groupService.removeUserFromGroupByEmail(groupId, email, ownerEmail);
        return ResponseEntity.ok().build();
    }




}
