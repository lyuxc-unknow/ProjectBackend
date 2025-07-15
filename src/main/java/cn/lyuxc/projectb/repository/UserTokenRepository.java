package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    UserToken findByUsername(String username);
    UserToken findByToken(String token);
}
