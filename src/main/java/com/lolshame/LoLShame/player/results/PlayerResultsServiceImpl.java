package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.TeamColorEnum;
import com.lolshame.LoLShame.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PlayerResultsServiceImpl implements PlayerResultsService {

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
        Optional<String> opponentPuuid = findLaneOpponent(playerPuuid, match);
        return (opponentPuuid.isPresent() ?
                match.getPlayerStats().get(opponentPuuid.get()) : new CorruptMatchDetails(playerPuuid));
    }

    private Optional<String> findLaneOpponent(String playerPuuid, Match match){
        PlayerMatchDetails playerDetails = match.getPlayerStats().get(playerPuuid);
        PlayedLaneEnum playerLane = playerDetails.getLane();
        TeamColorEnum playerTeam = playerDetails.getTeam();

                return match.getPlayerStats().entrySet().stream()
                .filter( v -> !(v.getValue().getTeam() == playerTeam))
                .filter( v -> v.getValue().getLane() == playerLane)
                .map(Map.Entry::getKey)
                        .findFirst();
    }

    private PlayerResults checkLaneAdvantage(PlayerMatchDetails playerMatchDetails, PlayerMatchDetails opponentPerformance){
        double killParticip = playerMatchDetails.getKillParticipation();
        long goldAdvantage = 0L;
        if(!opponentPerformance.getClass().equals(CorruptMatchDetails.class)){
            goldAdvantage = goldAdvantage(playerMatchDetails, opponentPerformance);
        }
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




}
