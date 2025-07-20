package cn.lyuxc.projectb.dto;

import lombok.Getter;
import lombok.Setter;

public class DrawRequest {
    @Getter
    @Setter
    private String poolId;
    @Getter
    @Setter
    private int count;
}
