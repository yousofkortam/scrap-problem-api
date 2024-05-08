package org.xjudge.service.scrapping;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xjudge.entity.*;
import org.xjudge.enums.OnlineJudge;
import org.xjudge.exception.ScrappingException;
import org.xjudge.repository.PropertyRepository;
import org.xjudge.repository.SectionRepository;
import org.xjudge.repository.ValueRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtCoderScrapping implements ScrappingStrategy {

    private final PropertyRepository propertyRepository;
    private final SectionRepository sectionRepository;
    private final ValueRepository valueRepository;

    @Override
    public Problem scrap(String contestId, String problemId) {
        String atCoderURL = "https://atcoder.jp/contests/";
        String targetProblem = atCoderURL + contestId + "/tasks/" + problemId;
        System.out.println(targetProblem);
        String contestLink = atCoderURL + contestId;
        Connection connection;
        Document problemDocument;
        try {
            connection = Jsoup.connect(targetProblem);
            problemDocument = connection.get();
        }catch (Exception e){
            throw new ScrappingException("Problem not found", HttpStatus.NOT_FOUND);
        }

        String problemTitle = problemDocument.select(".col-sm-12 .h2").getFirst().ownText().substring(4);
        String[] tmLimit = problemDocument.select(".col-sm-12").get(1).select("p").getFirst().text().split("/");
        String contestName = problemDocument.select(".contest-title").text();

        List<Property> properties = List.of(
                Property.builder().title("Time Limit").content(tmLimit[0].substring(11)).spoiler(false).build(),
                Property.builder().title("Memory Limit").content(tmLimit[1].substring(14)).spoiler(false).build()
        );

        propertyRepository.saveAll(properties);

        Elements parts = problemDocument.select(".lang-en .part");
        List<Section> problemSections = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < parts.size(); i++) {
            String title = parts.get(i).select("section > h3").text();
            String content = "<section>\n   " + parts.get(i).select("section > *:not(h3)").outerHtml() + "\n</section>";
            if (title.contains("Sample Input")) {
                title = "Sample " + ++counter;
                content = generateSampleTable(parts.get(i), parts.get(++i));
            }
            Value value = valueRepository.save(Value.builder().format("HTML").content(content).build());
            problemSections.add(Section.builder().title(title).value(value).build());
        }

        sectionRepository.saveAll(problemSections);

        return Problem.builder()
                .code(contestId+problemId)
                .onlineJudge(OnlineJudge.atcoder)
                .title(problemTitle)
                .problemLink(targetProblem)
                .contestName(contestName)
                .contestLink(contestLink)
                .prependHtml(getPrependHtml())
                .sections(problemSections)
                .properties(properties)
                .build();
    }

    private String generateSampleTable(Element input, Element output) {
        String sampleInput = input.select("section > pre").outerHtml();
        String sampleOutput = output.select("section > pre").outerHtml();
        String note = output.select("section > *:not(h3):not(pre)").outerHtml();
        return "<div class=\"sampleTests ps-5 pe-5\">\n" +
                "    <table class=\"table table-bordered sample\">\n" +
                "        <thead>\n" +
                "            <tr style=\"background-color:#ebebeb\">\n" +
                "                <th class=\"w-50\">Input</th>\n" +
                "                <th class=\"w-50\">Output</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td class=\"text-start\"> " + sampleInput + " </td>\n" +
                "                <td class=\"text-start\"> " + sampleOutput + " </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "    <section>" + note + "</section>" +
                "</div>\n";
    }

    private String getPrependHtml() {
        return """
                <!-- start -->
                <script src="https://img.atcoder.jp/public/ba514ee/js/lib/jquery-1.9.1.min.js"></script>
                <link rel="stylesheet" href="https://img.atcoder.jp/public/ba514ee/css/cdn/katex.min.css">
                <script defer="" src="https://img.atcoder.jp/public/ba514ee/js/cdn/katex.min.js"></script>
                <script defer="" src="https://img.atcoder.jp/public/ba514ee/js/cdn/auto-render.min.js"></script>
                <script>$(function () { $('var').each(function () { var html = $(this).html().replace(/<sub>/g, '_{').replace(/<\\/sub>/g, '}'); $(this).html('\\\\(' + html + '\\\\)'); }); });</script>
                <script>
                    var katexOptions = {
                        delimiters: [
                            { left: "$$", right: "$$", display: true },
                            { left: "\\\\(", right: "\\\\)", display: false },
                            { left: "\\\\[", right: "\\\\]", display: true }
                        ],
                        ignoredTags: ["script", "noscript", "style", "textarea", "code", "option"],
                        ignoredClasses: ["prettyprint", "source-code-for-copy"],
                        throwOnError: false
                    };
                    document.addEventListener("DOMContentLoaded", function () { renderMathInElement(document.body, katexOptions); });
                </script>
                <!-- End -->
                """;
    }
}
