package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.TeamColor;
import com.lolshame.LoLShame.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class PlayerResultsService {

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
                .collect(Collectors.joining());
    }

    private PlayerResults checkLaneAdvantage(PlayerMatchDetails playerMatchDetails, PlayerMatchDetails opponentPerformance){
        double killParticip = playerMatchDetails.getKillParticipation();
        long goldAdvantage = goldAdvantage(playerMatchDetails, opponentPerformance);

    }

    public PlayerResults providePlayerResults(List<Match> matchList, Player player){

    }

}
