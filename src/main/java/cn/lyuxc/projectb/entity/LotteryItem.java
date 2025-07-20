package cn.lyuxc.projectb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lottery_item")
public class LotteryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "pool_name", nullable = false)
    @Getter
    @Setter
    private String poolName;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;
}

