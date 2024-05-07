package org.xjudge.service.scrapping;

import org.xjudge.entity.Problem;

public interface Scrapping {
    Problem start(String contestId, String problemId);
}
