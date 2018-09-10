package io.anhkhue.jwtauthoritiesbased.repository;

import io.anhkhue.jwtauthoritiesbased.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
