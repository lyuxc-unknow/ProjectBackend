package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.dto.DrawRequest;
import cn.lyuxc.projectb.dto.LotteryResult;
import cn.lyuxc.projectb.entity.LotteryPool;
import cn.lyuxc.projectb.models.Pool;
import cn.lyuxc.projectb.repository.LotteryPoolRepository;
import cn.lyuxc.projectb.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lottery")
//@CrossOrigin // 允许跨域，视部署情况添加
public class LotteryController {

    private final LotteryService lotteryService;
    private final LotteryPoolRepository poolRepo;

    public LotteryController(LotteryService lotteryService, LotteryPoolRepository poolRepo) {
        this.lotteryService = lotteryService;
        this.poolRepo = poolRepo;
    }

    @PostMapping("/draw")
    public Map<String, List<String>> draw(@RequestBody DrawRequest req) {
        return Map.of("items", lotteryService.draw(req.getPoolId(), req.getCount()));
    }

    @GetMapping("/pools")
    public Map<String, List<String>> getPools() {
        List<String> poolIds = poolRepo.findAll()
                .stream()
                .map(LotteryPool::getPoolName)
                .toList();
        return Map.of("pools", poolIds);
    }

    @PostMapping("/pools")
    @SuppressWarnings("all")
    public ResponseEntity<String> createPool(@RequestBody Map<String, String> body) {
        String poolName = body.get("poolName");
        if (poolName == null || poolName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("poolName不能为空");
        }
        try {
            lotteryService.createPool(poolName.trim());
            return ResponseEntity.ok("创建成功");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/pool/{poolId}/items")
    public ResponseEntity<String> addItem(
            @PathVariable String poolId,
            @RequestBody Map<String, String> body) {
        String name = body.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("name不能为空");
        }
        try {
            lotteryService.addItemToPool(poolId, name.trim());
            return ResponseEntity.ok("添加成功");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
