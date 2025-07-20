package cn.lyuxc.projectb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tokens")
@NoArgsConstructor
public class UserToken {

    @Id
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private LocalDateTime updateTime;

    public UserToken(String username, String token) {
        this.username = username;
        this.token = token;
        this.updateTime = LocalDateTime.now();
    }
}
