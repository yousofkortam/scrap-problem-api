package org.xjudge.service.problem;

import org.xjudge.entity.Problem;

public interface ProblemService {
    Problem getProblem(String source, String code);
}
