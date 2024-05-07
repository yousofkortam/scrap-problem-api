package org.xjudge.service.problem;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xjudge.entity.Problem;
import org.xjudge.enums.OnlineJudge;
import org.xjudge.repository.ProblemRepository;
import org.xjudge.service.scrapping.Scrapping;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final Scrapping scrapping;

    @Override
    public Problem getProblem(String source, String code) {
        Optional<Problem> problem = problemRepository.findByCodeAndOnlineJudge(code, OnlineJudge.valueOf(source));
        return problem.orElseGet(() -> scrapCodeforcesProblem(code));
    }

    public Problem scrapCodeforcesProblem(String code) {
        String contestId = code.replaceAll("(\\d+).*", "$1");
        String problemId = code.replaceAll("\\d+(.*)", "$1");
        return problemRepository.save(scrapping.start(contestId, problemId));
    }

}
