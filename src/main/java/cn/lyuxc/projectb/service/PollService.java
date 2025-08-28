package cn.lyuxc.projectb.service;

import cn.lyuxc.projectb.entity.Option;
import cn.lyuxc.projectb.entity.Poll;
import cn.lyuxc.projectb.repository.OptionRepository;
import cn.lyuxc.projectb.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;

    public PollService(PollRepository pollRepository, OptionRepository optionRepository) {
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
    }

    public Poll getPoll(Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
    }

    public void vote(Long pollId, Long optionId, String voterId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        Option option = poll.getOptions().stream()
                .filter(o -> o.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option not found"));

        option.addVote();
        optionRepository.save(option);
    }

    public Poll createPoll(Poll poll) {
        poll.getOptions().forEach(opt -> opt.setPoll(poll));
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

}
