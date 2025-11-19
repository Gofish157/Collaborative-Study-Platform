package com.studyplatform.server.repositories;

import com.studyplatform.server.models.Task;

public interface TaskRepository {
    Task save(Task task);
    Task findById(Long id);
    void deleteById(Long id);
}
