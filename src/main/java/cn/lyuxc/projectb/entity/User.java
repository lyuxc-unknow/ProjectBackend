package cn.lyuxc.projectb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL 必须使用 IDENTITY
    private Long id;
    
    @Getter
    @Setter
    @Column(unique = true)
    private String username;
    
    @Getter
    @Setter
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

