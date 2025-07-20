package cn.lyuxc.projectb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lottery_pool")
public class LotteryPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "pool_name", unique = true, nullable = false)
    @Getter
    @Setter
    private String poolName;
}
