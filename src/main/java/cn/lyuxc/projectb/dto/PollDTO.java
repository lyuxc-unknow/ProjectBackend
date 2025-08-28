package cn.lyuxc.projectb.dto;

import cn.lyuxc.projectb.entity.Poll;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PollDTO {
    // getters
    private Long id;
    private String title;
    private List<OptionDTO> options;

    public PollDTO(Poll poll) {
        this.id = poll.getId();
        this.title = poll.getTitle();
        this.options = poll.getOptions().stream()
                .map(OptionDTO::new)
                .collect(Collectors.toList());
    }

}
