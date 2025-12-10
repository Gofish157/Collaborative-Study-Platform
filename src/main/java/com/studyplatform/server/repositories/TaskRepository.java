package com.studyplatform.server.repositories;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.Task;
import com.studyplatform.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByGroup(Group group);
    List<Task> findByUser(User user);

}