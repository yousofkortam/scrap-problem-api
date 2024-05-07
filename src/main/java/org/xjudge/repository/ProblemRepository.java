package org.xjudge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xjudge.entity.Problem;
import org.xjudge.enums.OnlineJudge;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findByCodeAndOnlineJudge(String code, OnlineJudge onlineJudge);
}
