package com.lolshame.LoLShame.match;

import com.lolshame.LoLShame.RiotApiService;
import com.lolshame.LoLShame.player.results.PlayedLaneEnum;
import com.lolshame.LoLShame.player.results.PlayerMatchDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchService {

    @Autowired
    private RiotApiService apiService;

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
        log.info("Extracted player substrings for matchId: "+ matchId );

        Map<String, PlayerMatchDetails> playerPerformanceStrings = computePlayerPerformanceMap(playerSubstrings);

        log.info("Successfully parsed data for matchId: " + matchId);

        return Match.builder()
                .matchId(matchId)
                .gameStartTimestamp(timestamp)
                .playerStats(playerPerformanceStrings)
                .build();

    }

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

    private static List<String> extractPlayerSubstrings(String response) {

        List<String> playerSubstrings = new LinkedList<>();

        //skip first metadata group
        int index = 0;
        int indexEnd =  response.indexOf("allInPings", index);
        index = indexEnd;


        for(int i = 0; i <10; i++){
            indexEnd =  response.indexOf("allInPings", index + 1);
            if(isValidIndex(indexEnd)){
                playerSubstrings.add(response.substring(index, indexEnd));
                index = indexEnd;
            }
            else{
                playerSubstrings.add(response.substring(index));
                break;
            }

        }
        return playerSubstrings;
    }

    private static boolean isValidIndex(int index){
        return !(index == -1);
    }


    private String getPuuidFromSubstring(String substring){
        return parse("puuid", substring);
    }

    public PlayerMatchDetails getPlayerPerformanceFromSubstring(String substring){

        PlayerMatchDetails details = new PlayerMatchDetails();

        details.setPerfectGame((Integer.parseInt(parse("perfectGame", substring))));
        details.setKillParticipation(Double.parseDouble(parse("killParticipation", substring)));
        details.setGoldEarned(Long.parseLong(parse("goldEarned", substring)));
        details.setVisionScoreAdvantageLaneOpponent(Double.parseDouble(parse("visionScoreAdvantageLaneOpponent", substring)));
        details.setWin(Boolean.parseBoolean(parse("win", substring)));
        details.setLane(PlayedLaneEnum.valueOf(parse("individualPosition", substring)));
        details.setTeam(TeamColorEnum.numericValueOf(parse("teamId", substring)));

        return details;
    }

    public Map<String, PlayerMatchDetails> computePlayerPerformanceMap(List<String> substrings){
        return substrings.stream()
                .collect(Collectors.toMap(this::getPuuidFromSubstring, this::getPlayerPerformanceFromSubstring));
    }



}
