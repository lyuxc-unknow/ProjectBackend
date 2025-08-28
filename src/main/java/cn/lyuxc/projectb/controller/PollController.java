package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.dto.PollDTO;
import cn.lyuxc.projectb.entity.Poll;
import cn.lyuxc.projectb.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    // 获取所有投票列表（仅返回 id 和 title）
    @GetMapping
    public ResponseEntity<List<PollDTO>> getAllPolls() {
        List<PollDTO> list = pollService.getAllPolls()
                .stream()
                .map(PollDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // 获取单个投票详情
    @GetMapping("/{pollId}")
    public ResponseEntity<PollDTO> getPoll(@PathVariable Long pollId) {
        try {
            Poll poll = pollService.getPoll(pollId);
            return ResponseEntity.ok(new PollDTO(poll));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 提交投票，返回最新投票数据
    @PostMapping("/{pollId}/vote/{optionId}")
    public ResponseEntity<PollDTO> vote(
            @PathVariable Long pollId,
            @PathVariable Long optionId,
            @RequestParam String voterId
    ) {
        try {
            pollService.vote(pollId, optionId, voterId);
            Poll poll = pollService.getPoll(pollId);
            return ResponseEntity.ok(new PollDTO(poll));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 新建投票
    @PostMapping
    public ResponseEntity<PollDTO> createPoll(@RequestBody Poll poll) {
        Poll saved = pollService.createPoll(poll);
        return ResponseEntity.ok(new PollDTO(saved));
    }
}