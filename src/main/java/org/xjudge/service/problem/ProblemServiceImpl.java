package org.xjudge.service.problem;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xjudge.entity.Problem;
import org.xjudge.enums.OnlineJudge;
import org.xjudge.repository.ProblemRepository;
import org.xjudge.service.scrapping.ScrappingStrategy;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final Map<OnlineJudge, ScrappingStrategy> scrappingStrategies;

    @Override
    public Problem getProblem(String source, String contestId, String problemId) {
        String code = contestId + problemId;
        Optional<Problem> problem = problemRepository.findByCodeAndOnlineJudge(code, OnlineJudge.valueOf(source.toLowerCase()));
        return problem.orElseGet(() -> scrapProblem(source, contestId, problemId));
    }

    private Problem scrapProblem(String source, String contestId, String problemId) {
        ScrappingStrategy strategy = scrappingStrategies.get(OnlineJudge.valueOf(source.toLowerCase()));
        Problem problem = strategy.scrap(contestId, problemId);
        return problemRepository.save(problem);
    }

}
