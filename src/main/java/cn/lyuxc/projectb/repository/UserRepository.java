package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
