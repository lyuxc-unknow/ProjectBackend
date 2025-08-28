package cn.lyuxc.projectb.repository;

import cn.lyuxc.projectb.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
