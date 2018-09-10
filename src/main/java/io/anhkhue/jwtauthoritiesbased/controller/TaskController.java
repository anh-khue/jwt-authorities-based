package io.anhkhue.jwtauthoritiesbased.controller;

import io.anhkhue.jwtauthoritiesbased.model.Task;
import io.anhkhue.jwtauthoritiesbased.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.status(OK)
                             .body(taskRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK)
                                 .body(taskRepository.findById(Integer.parseInt(id))
                                                     .orElseThrow(Exception::new));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                                 .build();
        }
    }

    @PostMapping
    public void signUp(@RequestBody Task task) {
        taskRepository.save(task);
    }
}
