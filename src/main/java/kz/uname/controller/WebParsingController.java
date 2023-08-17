package kz.uname.controller;

import kz.uname.constants.AllScoresCoff;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Kairat Zhiger
 * at 12.08.2023
 */
@RestController
@RequiredArgsConstructor
public class WebParsingController {

    @GetMapping("/do")
    public void send() throws IOException {
        Document doc = Jsoup.connect("https://allscores.club/league.php?sport=&champ=1857")
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        var teams = getAllScoresFilteredTeams(doc);
        teams.entrySet().stream()
                .filter(stringStringEntry ->isValid(stringStringEntry.getValue()))
                .forEach(System.out::println);

    }

    private boolean isValid(String coff){
        return firstCoffDigit(coff)>= AllScoresCoff.COFF_LOWER_BORDER;
    }

    private Double firstCoffDigit(String coff){
        var firstDigit=coff.split("-")[0].trim();
        return Double.parseDouble(firstDigit);
    }


    private Map<String, String> getAllScoresFilteredTeams(Document document) {
        var div = document.body().select("div#leaguetable").select("table");
        var table = div.select("tbody").select("tr");
        var teamsMap = new HashMap<String, String>();
        String name = null;
        String coff = null;
        for (int i = 3; i < table.size(); i++) {
            var td = table.get(i).select("td");
            if (td.size() < 7 || td.text().isBlank()) continue;

            if (td.hasClass("fs2_team") || td.hasClass("fs22_team"))
                name = td.select("a").text();

            if (td.hasClass("fs2") || td.hasClass("fs22"))
                coff = td.get(7).text();

            if (!name.isBlank() && !coff.isBlank())
                teamsMap.put(name, coff);

        }
        return teamsMap;

    }
}
