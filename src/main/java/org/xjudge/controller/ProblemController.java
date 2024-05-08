package org.xjudge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xjudge.entity.Problem;
import org.xjudge.service.problem.ProblemService;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/problem/{source}/{contestId}/{problemId}")
    public String scrapProblem(@PathVariable String source,
                               @PathVariable String contestId,
                               @PathVariable String problemId, Model model) {
        Problem problem = problemService.getProblem(source, contestId, problemId);
        model.addAttribute("problem", problem);
        return "problem-description";
    }

}
