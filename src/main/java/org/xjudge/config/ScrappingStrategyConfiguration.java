package org.xjudge.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xjudge.enums.OnlineJudge;
import org.xjudge.service.scrapping.AtCoderScrapping;
import org.xjudge.service.scrapping.CodeforcesScrapping;
import org.xjudge.service.scrapping.ScrappingStrategy;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ScrappingStrategyConfiguration {

    private final CodeforcesScrapping codeforcesScrapping;
    private final AtCoderScrapping atCoderScrapping;

    @Bean
    public Map<OnlineJudge, ScrappingStrategy> scrappingStrategies() {
        Map<OnlineJudge, ScrappingStrategy> strategies = new HashMap<>();
        strategies.put(OnlineJudge.codeforces, codeforcesScrapping);
        strategies.put(OnlineJudge.atcoder, atCoderScrapping);
        return strategies;
    }
}
