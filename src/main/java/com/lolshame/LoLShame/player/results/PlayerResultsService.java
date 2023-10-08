package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.TeamColor;
import com.lolshame.LoLShame.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Component
public class PlayerResultsService {

    @Autowired
    ResultsCalculator calculator;

    public PlayerResults providePlayerResults(List<Match> matchList, Player player){
        List<PlayerResults> resultsPerMatch = matchList.stream().map(v -> this.providePlayerResults(v, player)).toList();

        return calculator.getResultsFromList(resultsPerMatch);
    }

    public PlayerResults providePlayerResults(Match match, Player player){
        PlayerMatchDetails playerMatchDetails = match.getPlayerStats().get(player.getPuuid());
        PlayerMatchDetails opponentDetails = getOpponentPerformance(match, player.getPuuid());

        return checkLaneAdvantage(playerMatchDetails, opponentDetails);

    }

    private PlayerMatchDetails getOpponentPerformance(Match match, String playerPuuid){
        String opponentPuuid = findLaneOpponent(playerPuuid, match);
        return match.getPlayerStats().get(opponentPuuid);
    }

    private String findLaneOpponent(String playerPuuid, Match match){
        PlayerMatchDetails playerDetails = match.getPlayerStats().get(playerPuuid);
        PlayedLaneEnum playerLane = playerDetails.getLane();
        TeamColor playerTeam = playerDetails.getTeam();

        return match.getPlayerStats().entrySet().stream()
                .filter( v -> !(v.getValue().getTeam() == playerTeam))
                .filter( v -> v.getValue().getLane() == playerLane)
                .map(Map.Entry::getKey)
                .collect(PlayerResultsService.singleElementCollector());
    }

    private PlayerResults checkLaneAdvantage(PlayerMatchDetails playerMatchDetails, PlayerMatchDetails opponentPerformance){
        double killParticip = playerMatchDetails.getKillParticipation();
        long goldAdvantage = goldAdvantage(playerMatchDetails, opponentPerformance);
        double visionScoreAdvantage = visionScoreAdvantage(playerMatchDetails);
        boolean win = playerMatchDetails.isWin();

        return PlayerResults.builder()
                .killParticipation(killParticip)
                .goldAdvantageAt15(goldAdvantage)
                .visionScoreAdvantageLaneOpponent(visionScoreAdvantage)
                .win(win)
                .build();
    }

    private long goldAdvantage(PlayerMatchDetails player, PlayerMatchDetails opponent){
        return player.getGoldEarned() - opponent.getGoldEarned();
    }

    private double visionScoreAdvantage(PlayerMatchDetails player){
        return player.getVisionScoreAdvantageLaneOpponent();
    }



    public static <T> Collector<T, ?, T> singleElementCollector() {
        return Collector.of(
                () -> {
                    Object[] container = new Object[1];
                    container[0] = null;
                    return container;
                },
                (container, element) -> {
                    if (container[0] == null) {
                        container[0] = element;
                    } else {
                        throw new IllegalStateException("Multiple elements found in the stream.");
                    }
                },
                (container1, container2) -> {
                    if (container1[0] == null) {
                        return container2;
                    } else if (container2[0] == null) {
                        return container1;
                    } else {
                        throw new IllegalStateException("Multiple elements found in the stream.");
                    }
                },
                container -> (T) container[0]
        );
    }

}
