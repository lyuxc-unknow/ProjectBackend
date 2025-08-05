package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.entity.TodoItem;
import cn.lyuxc.projectb.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // 获取所有 todoItem 或按状态筛选
    @GetMapping
    public List<TodoItem> getAll(@RequestParam(required = false) Boolean completed) {
        return service.getAllTodos(completed);
    }

    // 添加 todoItem
    @PostMapping
    public TodoItem addTodo(@RequestBody TodoItem todo) {
        return service.addTodo(todo);
    }

    // 删除 todoItem
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
    }

    // 修改完成状态
    @PutMapping("/{id}/completed")
    public TodoItem updateStatus(@PathVariable Long id, @RequestBody boolean completed) {
        return service.updateStatus(id, completed).orElseThrow(() -> new RuntimeException("TODO not found"));
    }
}
