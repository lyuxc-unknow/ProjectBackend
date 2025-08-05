package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByCompleted(boolean completed);
}
