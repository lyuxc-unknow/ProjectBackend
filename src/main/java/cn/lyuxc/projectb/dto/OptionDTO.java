package cn.lyuxc.projectb.dto;

import cn.lyuxc.projectb.entity.Option;
import lombok.Getter;

@Getter
public class OptionDTO {
    // getters
    private Long id;
    private String optionText;
    private int votes;

    public OptionDTO(Option option) {
        this.id = option.getId();
        this.optionText = option.getOptionText();
        this.votes = option.getVotes();
    }

}
