package com.lolshame.LoLShame;

import com.lolshame.LoLShame.match.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchServiceTest {

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
        String paramKey = "nmKSVsE7N5kK1Yq0OHunj0IKvnrC1MOy4SbpukwM8Rr-E7EAZroL8BIEKi0Zu8GOdnqxumEq5aBoHQ";

        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/Match-test.json")));
        assertEquals(paramKey, MatchService.parse(param, input));

    }

    @Test
    public void testBuildMatch() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/Match-test.json")));

        System.out.println(matchService.buildMatchFromStringResponse(input).toString());
    }

}
