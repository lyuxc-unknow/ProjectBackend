package cn.lyuxc.projectb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todo_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean completed;
}
