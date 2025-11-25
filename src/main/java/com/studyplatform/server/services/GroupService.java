package com.studyplatform.server.services;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(Long Id){
        return groupRepository.findById(Id);
    }

    public Group createGroup(String name, String description, Long createBy){
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setCreatedBy(createBy.toString());
        group.setCreatedAt(LocalDateTime.now());

        return groupRepository.save(group);
    }

    public Group updateGroup(Long id, String name, String description){
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        group.setName(name);
        group.setDescription(description);

        return groupRepository.save(group);
    }

    public void deleteGroup(Long id){
        groupRepository.deleteById(id);
    }
}
