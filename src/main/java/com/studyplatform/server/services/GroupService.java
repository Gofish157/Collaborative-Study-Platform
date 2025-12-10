package com.studyplatform.server.services;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.User;
import com.studyplatform.server.repositories.GroupRepository;
import com.studyplatform.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    public Group getGroupById(Long Id){
        return groupRepository.findById(Id).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public Group createGroup(String name, String description, String createBy){
        User creator = userService.getUserByEmail(createBy);
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setCreatedBy(createBy);
        group.setCreatedAt(LocalDateTime.now());
        group.getUsers().add(creator);


        return groupRepository.save(group);
    }

    public Group updateGroup(Long id, String name, String description, String userEmail){
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        if (!group.getCreatedBy().equals(userEmail)) {
            throw new RuntimeException("Unauthorized to update this group");
        }
        group.setName(name);
        group.setDescription(description);

        return groupRepository.save(group);
    }

    public void deleteGroup(Long id){
        if (!groupRepository.existsById(id)) throw new RuntimeException("Group not found");
        groupRepository.deleteById(id);
    }

    public void addUserToGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userService.getUserById(userId);
        group.getUsers().add(user);
        groupRepository.save(group);
    }

    public void removeUserFromGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userService.getUserById(userId);
        group.getUsers().remove(user);
        groupRepository.save(group);
    }

    public void addUserToGroupByEmail(Long groupId, String userEmail, String requesterEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getCreatedBy().equals(requesterEmail)) {
            throw new RuntimeException("Only group owner can add users");
        }

        User user = userService.getUserByEmail(userEmail);

        if (group.getUsers().contains(user)) {
            throw new RuntimeException("User already in group");
        }

        group.getUsers().add(user);
        groupRepository.save(group);
    }

    public void removeUserFromGroupByEmail(Long groupId, String emailToRemove, String actorEmail) {
        Group group = getGroupById(groupId);

        if (!group.getCreatedBy().equals(actorEmail)) {
            throw new RuntimeException("Only group owner can remove users");
        }

        group.getUsers().removeIf(u -> u.getEmail().equals(emailToRemove));
        groupRepository.save(group);
    }



}
