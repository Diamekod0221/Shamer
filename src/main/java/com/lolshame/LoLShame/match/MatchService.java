package com.lolshame.LoLShame.match;

import com.lolshame.LoLShame.RiotApiService;
import com.lolshame.LoLShame.RiotApiServiceImpl;
import com.lolshame.LoLShame.player.results.CorruptMatchDetails;
import com.lolshame.LoLShame.player.results.PlayedLaneEnum;
import com.lolshame.LoLShame.player.results.PlayerMatchDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchService {

    @Autowired
    private RiotApiService apiService;

    public List<Match> getMatchList(String puuid) throws HttpClientErrorException{
        return fetchMatches(getMatchIdsList(puuid));
    }

    private List<String> getMatchIdsList(String puuid){
        return apiService.fetchMatchIds(puuid);
    }

    private List<Match> fetchMatches(List<String> matchIds) throws HttpClientErrorException {
        return matchIds.stream()
                .map(apiService::fetchMatchData)
                .filter(v -> !v.isEmpty())
                .map(this::buildMatchFromStringResponse)
                .filter(v -> (!v.getClass().equals(CorruptMatch.class)))
                .collect(Collectors.toList());
    }

    public Match buildMatchFromStringResponse(String response){
        String matchId = parse("matchId", response);

        try {
            return buildMatchFromResponseAndId(response, matchId);
        }
        catch (IllegalArgumentException e){
            return handleCorruptResponse(matchId);
        }
    }

    private static CorruptMatch handleCorruptResponse(String matchId) {
        log.warn("Corrupt data for matchId: " + matchId
        + "falling back, returning CorruptMatch to queue.");
        return new CorruptMatch(matchId);
    }

    private Match buildMatchFromResponseAndId(String response, String matchId) {
        Long timestamp = Long.parseLong(parse("gameStartTimestamp", response));

        List<String> playerSubstrings = extractPlayerSubstrings(response);
        log.info("Extracted player substrings for matchId: "+ matchId);

        Map<String, PlayerMatchDetails> playerPerformanceStrings = computePlayerPerformanceMap(playerSubstrings);
        if(hasCorruptPlayerMatchDetails(playerPerformanceStrings)){
            return new CorruptMatch(matchId);
        }

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

    private <T> T parse(String paramName, String substring, Function<String,T> typingFunction) throws IllegalArgumentException{
        log.debug("Parsing param: " + paramName
                + "\n Substring: \n" +substring);
        return typingFunction.apply(parse(paramName, substring));
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

    public PlayerMatchDetails getPlayerPerformanceFromSubstring(String substring) {

        try{
            return parsePlayerMatchDetailsFromSubstring(substring);
        }
        catch(IllegalArgumentException e){
            log.debug("Couldn't parse on substring: " + substring);
            return new CorruptMatchDetails(substring);
        }

    }

    private PlayerMatchDetails parsePlayerMatchDetailsFromSubstring(String substring) {
        PlayerMatchDetails details = new PlayerMatchDetails();

        details.setPerfectGame(parse("perfectGame", substring, Integer::parseInt));
        details.setKillParticipation(parse("killParticipation", substring, Double::parseDouble));
        details.setGoldEarned(parse("goldEarned", substring, Long::parseLong));
        details.setVisionScoreAdvantageLaneOpponent(parse("visionScoreAdvantageLaneOpponent", substring, Double::parseDouble));
        details.setWin(parse("win", substring, Boolean::parseBoolean));
        details.setLane(parse("individualPosition", substring, PlayedLaneEnum::valueOf));
        details.setTeam(parse("teamId", substring, TeamColorEnum::numericValueOf));

        return details;
    }


    public Map<String, PlayerMatchDetails> computePlayerPerformanceMap(List<String> substrings) {
        return substrings.stream()
                .collect(Collectors.toMap(this::getPuuidFromSubstring, this::getPlayerPerformanceFromSubstring));
    }

    private boolean hasCorruptPlayerMatchDetails(Map<String, PlayerMatchDetails> playerMatchDetailsMap){
        return playerMatchDetailsMap.values().stream().anyMatch(v -> v.getClass().equals(CorruptMatchDetails.class));
    }




}
