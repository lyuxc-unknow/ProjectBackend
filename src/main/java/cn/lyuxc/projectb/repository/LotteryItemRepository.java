package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.LotteryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotteryItemRepository extends JpaRepository<LotteryItem, Long> {
    List<LotteryItem> findByPoolName(String poolName);
}
