package com.lolshame.LoLShame.match;

import com.lolshame.LoLShame.RiotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private RiotApiService apiService;

    public static String parse(String paramName, String payload) {
        String regex = "\"" + paramName + "\":(\\{[^\\{\\}]*\\}|\\[[^\\[\\]]*\\]|\"[^\"]*\"|[^,\\{\\}]+)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(payload);

        if (matcher.find()) {
            String extractedValue = matcher.group(1);

            extractedValue = extractedValue.replaceAll("[{}\\]]", "").replace("\"", "").strip();

            return extractedValue;
        } else {
            return "paramNotFound";
        }
    }


    public List<Match> getMatchList(String puuid){
        return fetchMatches(getMatchIdsList(puuid));
    }

    private List<String> getMatchIdsList(String puuid){
        return apiService.fetchMatchIds(puuid);
    }

    private List<Match> fetchMatches(List<String> matchIds){
        return matchIds.stream()
                .map(apiService::fetchMatchData)
                .filter(v -> !v.isEmpty())
                .map(this::buildMatchFromStringResponse)
                .collect(Collectors.toList());
    }

    public Match buildMatchFromStringResponse(String response){

        String matchId = parse("matchId", response);
        Long timestamp = Long.parseLong(parse("gameStartTimestamp", response));

        List<String> playerSubstrings = extractPlayerSubstrings(response);

        List<String> teamBlue = getTeam(playerSubstrings, TeamColor.BLUE);
        List<String> teamRed = getTeam(playerSubstrings, TeamColor.RED);

        Map<String, PlayerPerformance> playerPerformanceStrings = playerSubstrings.stream()
                .collect(Collectors.toMap(this::getPuuidFromSubstring, this::getPlayerPerformanceFromSubstring));

        return Match.builder()
                .matchId(matchId)
                .gameStartTimestamp(timestamp)
                .teamBlue(teamBlue)
                .teamRed(teamRed)
                .playerStats(playerPerformanceStrings)
                .build();

    }

    private List<String> getTeam(List<String> response, TeamColor team){
        return response.stream()
                .filter(s -> parse("teamId", s).equals(TeamColor.toStringVal(team)))
                .map(s -> parse("puuid", s))
                .toList();

    }

    private static List<String> extractPlayerSubstrings(String response) {

        List<String> playerSubstrings = new LinkedList<>();

        //skip first metadata group
        int index = 0;
        int indexEnd =  response.indexOf("allInPings", index);
        index = indexEnd;


        for(int i = 0; i <10; i++){
            indexEnd =  response.indexOf("allInPings", index );
            System.out.println(response.substring(index));
            playerSubstrings.add(response.substring(index, indexEnd));
            index = indexEnd;
        }
        return playerSubstrings;
    }

    private String getPuuidFromSubstring(String substring){
        return parse("puuid", substring);
    }

    private PlayerPerformance getPlayerPerformanceFromSubstring(String substring){

        PlayerPerformance performance = new PlayerPerformance();

        performance.setPerfectGame((Integer.getInteger(parse("perfectGame", substring))));
        performance.setKillParticipation(Double.parseDouble(parse("killParticipation", substring)));
        performance.setGoldEarned(Long.parseLong(parse("goldEarned", substring)));
        performance.setVisionScoreAdvantageLaneOpponent(Double.parseDouble(parse("visionScoreAdvantageLaneOpponent", substring)));
        performance.setWin(Boolean.getBoolean(parse("win", substring)));

        return performance;
    }



}
