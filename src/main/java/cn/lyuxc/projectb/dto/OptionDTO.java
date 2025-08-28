package cn.lyuxc.projectb.dto;

import cn.lyuxc.projectb.entity.Option;
import lombok.Getter;

@Getter
public class OptionDTO {
    // getters
    private final Long id;
    private final String optionText;
    private final int votes;

    public OptionDTO(Option option) {
        this.id = option.getId();
        this.optionText = option.getOptionText();
        this.votes = option.getVotes();
    }

}
