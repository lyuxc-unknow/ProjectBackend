package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.entity.Poll;
import cn.lyuxc.projectb.service.PollService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll")
@CrossOrigin
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    // 获取投票详情
    @GetMapping("/{pollId}")
    public Poll getPoll(@PathVariable Long pollId) {
        return pollService.getPoll(pollId);
    }

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    // 提交投票
    @PostMapping("/{pollId}/vote/{optionId}")
    public void vote(
            @PathVariable Long pollId,
            @PathVariable Long optionId,
            @RequestParam String voterId
    ) {
        pollService.vote(pollId, optionId, voterId);
    }

    // 新建投票（可选）
    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }
}
