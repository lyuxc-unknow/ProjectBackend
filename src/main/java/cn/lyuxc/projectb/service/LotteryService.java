package cn.lyuxc.projectb.service;

import cn.lyuxc.projectb.entity.LotteryItem;
import cn.lyuxc.projectb.entity.LotteryPool;
import cn.lyuxc.projectb.repository.LotteryItemRepository;
import cn.lyuxc.projectb.repository.LotteryPoolRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LotteryService {

    private final LotteryPoolRepository poolRepo;
    private final LotteryItemRepository itemRepo;

    public LotteryService(LotteryPoolRepository poolRepo, LotteryItemRepository itemRepo) {
        this.poolRepo = poolRepo;
        this.itemRepo = itemRepo;
    }

    public List<String> draw(String poolId, int count) {
        List<LotteryItem> items = itemRepo.findByPoolName(poolId);
        if (items.isEmpty()) return List.of();

        Random rand = new Random();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            LotteryItem selected = items.get(rand.nextInt(items.size()));
            results.add(selected.getName());
        }

        return results;
    }

    public void createPool(String poolName) {
        if (poolRepo.existsByPoolName(poolName)) {
            throw new RuntimeException("奖池名称已存在");
        }

        LotteryPool pool = new LotteryPool();
        pool.setPoolName(poolName);
        poolRepo.save(pool);
    }

    public void addItemToPool(String poolId, String itemName) {
        LotteryPool pool = poolRepo.findByPoolName(poolId)
                .orElseThrow(() -> new RuntimeException("奖池不存在"));

        LotteryItem item = new LotteryItem();
        item.setPoolName(pool.getPoolName());
        item.setName(itemName);
        itemRepo.save(item);
    }
}


