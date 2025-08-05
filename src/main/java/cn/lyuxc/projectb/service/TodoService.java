package cn.lyuxc.projectb.service;

import cn.lyuxc.projectb.entity.TodoItem;
import cn.lyuxc.projectb.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<TodoItem> getAllTodos(Boolean completed) {
        if (completed != null) {
            return repository.findByCompleted(completed);
        }
        return repository.findAll();
    }

    public TodoItem addTodo(TodoItem item) {
        return repository.save(item);
    }

    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }

    public Optional<TodoItem> updateStatus(Long id, boolean completed) {
        return repository.findById(id).map(todo -> {
            todo.setCompleted(completed);
            return repository.save(todo);
        });
    }
}
