package com.lolshame.LoLShame;

import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.MatchService;
import com.lolshame.LoLShame.player.results.PlayerMatchDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchServiceTests {

    private MatchService matchService;

    @BeforeEach
    public void setup(){
        this.matchService = new MatchService();
    }

    @Test
    public void testParserString() throws IOException {
        String param = "name";
        String paramKey = "Diamekod";

        List<String> myList = List.of(param);

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/testing-json.json")));
        assertEquals(paramKey, MatchService.parse(param, input));

    }

    @Test
    public void testParserInt() throws IOException {
        String param = "profileIconId";
        int paramKey = 4802;

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/testing-json.json")));
        assertEquals(String.valueOf(paramKey), MatchService.parse(param, input));

    }

    @Test
    public void testParserEdgeCaseEnd() throws IOException {
        String param = "summonerLevel";
        int paramKey = 269;

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/testing-json.json")));
        assertEquals(String.valueOf(paramKey), MatchService.parse(param, input));

    }

    @Test
    public void testParserFromMatch() throws IOException {
        String param = "puuid";
        String paramKey = "LrzhRzwlynzi77bimwDBjBmhyBN4jajWOBN38Sw2HKEBNMtesEa_5gtg9NCTcbpgSyJRg4d5NYShRA";

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/Match-test.json")));
        assertEquals(paramKey, MatchService.parse(param, input));

    }

    @Test
    public void testGetPlayerPerformance(){
        String input = String.valueOf(new File("src/test/resources/playerStatsSubstring.txt"));

        PlayerMatchDetails performance = new PlayerMatchDetails();

        performance.setPerfectGame(0);
        performance.setKillParticipation(0.7619047619047619);
        performance.setGoldEarned(14611);
        performance.setVisionScoreAdvantageLaneOpponent(-0.028629302978515625);
        performance.setWin(false);

        assertEquals(performance, matchService.getPlayerPerformanceFromSubstring(input));


    }

    @Test
    public void testBuildMatch() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/Match-test.json")));
        Match expected = this.buildTestMatch();

        Match actual = matchService.buildMatchFromStringResponse(input);

        assertEquals(expected.getMatchId(), actual.getMatchId());
        assertEquals(expected.getGameStartTimestamp(), actual.getGameStartTimestamp());

        Assertions.assertTrue(collectionEqualUnordered(expected.getPlayerStats().values(), actual.getPlayerStats().values()));
        Assertions.assertTrue(collectionEqualUnordered(expected.getPlayerStats().values(), actual.getPlayerStats().values()));
    }

    private Match buildTestMatch(){

        Map<String, PlayerMatchDetails> playerPerformanceMap = this.getTestPlayerStats();

        return Match.builder()
                .matchId("EUN1_3464618691")
                .gameStartTimestamp(Long.valueOf("1696105973525"))
                .playerStats(playerPerformanceMap)
                .build();
    }

    private Map<String, PlayerMatchDetails> getTestPlayerStats(){
                return Map.of(
                        "9SCCXakekcNPVP37x4XR5lqTThQlSwsc929LzzLrVQF4tf_tifu2apqVq9THOmYRcdEBtgaTet4IIw=",
                        new PlayerMatchDetails(0, 0.7619047619047619, 14611, -0.028629302978515625, false),
                        "LrzhRzwlynzi77bimwDBjBmhyBN4jajWOBN38Sw2HKEBNMtesEa_5gtg9NCTcbpgSyJRg4d5NYShRA",
                        new PlayerMatchDetails(0, 0.6190476190476191, 9643, 0.5453664064407349, false),
                        "DqjN3I0N7JRvux1sW2RvlI-OJI7FWDzlrLw0v5a2tTT09Trt8PIUMqBrmW4_DLHZzvkwt-hYYBl4Xw=",
                        new PlayerMatchDetails(0, 0.47619047619047616, 6765, -0.6462412476539612, false),
                        "nmKSVsE7N5kK1Yq0OHunj0IKvnrC1MOy4SbpukwM8Rr-E7EAZroL8BIEKi0Zu8GOdnqxumEq5aBoHQ=",
                        new PlayerMatchDetails(0, 0.45, 12581, 0.4806363582611084, false),
                        "SB4hBc5zf5zXlJ7oGnMFyfqqw_nfm1EbIuDrgNF6pk_jn0ThTQHtVd7-8Uf7fTZIB96ta1AAoHxWsg=",
                        new PlayerMatchDetails(0, 0.42857142857142855, 7666, -0.3246147632598877, false),
                        "iI5fVkIJPmBUaX3wBIIFJlqIHB2bcnSPH8PEu03_vLXJ6tGBRtTRniGXwXgbySPozLYsFuEQEr310A=",
                        new PlayerMatchDetails(0, 0.7142857142857143, 5764, -0.5763092637062073, false),
                        "N4avvF-FhEzW0XpIQX_EbLrQvey6nuP73jgyq892ZgXl-mJPihGLz4cxrtUWaWLO1fTt6OPSNxCnnA=",
                        new PlayerMatchDetails(0, 0.1, 9227, -0.3529042601585388, false),
                        "p6zWi6p8bVzilHeKwroXl5zBPl1KfULfkeGfAip-3O7LDJcdUCYVcY9ehDPQVZ9mYHYo9esE5jYmDA=",
                        new PlayerMatchDetails(0, 0.4, 10992, 1.8267850875854492, false),
                        "-EoA2BgNZeAQ_B_XIXx8Ckfzih1TEqAoeRiCVwi26iV0j6SCpqGR66ZTVO6M9JY461RDaG64sI4Rxg=",
                        new PlayerMatchDetails(0, 0.575, 15618, 0.029473066329956055, false)
                );
    }


    private <T> boolean collectionEqualUnordered(Collection<T> list1, Collection<T> list2){
        if(list1.size() != list2.size()){
            return false;
        }

        Collection<T> copyOfList2 = new ArrayList<>(list2);
        for (T item: list1){
            if(!copyOfList2.remove(item)){
                return false;
            }
        }
        return copyOfList2.isEmpty();
    }

}
