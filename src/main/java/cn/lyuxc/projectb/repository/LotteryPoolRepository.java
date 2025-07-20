package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.LotteryPool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotteryPoolRepository extends JpaRepository<LotteryPool, Long> {
    Optional<LotteryPool> findByPoolName(String poolName);
    boolean existsByPoolName(String poolName);
}
