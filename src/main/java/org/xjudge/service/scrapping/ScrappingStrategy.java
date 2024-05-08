package org.xjudge.service.scrapping;

import org.xjudge.entity.Problem;

public interface ScrappingStrategy {
    Problem scrap(String contestId, String problemId);
}
