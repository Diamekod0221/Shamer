package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.caching.PlayerResultsEntity;
import lombok.*;

import java.util.Collection;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PlayerResults {

    private double killParticipation;

    private long goldAdvantageAt15;

    private double visionScoreAdvantageLaneOpponent;

    private boolean win;

    public boolean areShamingResults(){
        return (
                hasMoreThanNShameIndices(2) || !win
        ) ;
    }

    private boolean hasMoreThanNShameIndices(int n){
        Collection<Boolean> resultList = booleanizeResults(this).values();
        return resultList.stream().mapToInt(v -> Boolean.compare(v, false)).sum() > n;
    }


    public static Map<String, Boolean> booleanizeResults(PlayerResults results){
        return Map.of(
                "alone", results.getKillParticipation() < 0.6,
                "poor", results.getGoldAdvantageAt15() < 300,
                "blind", results.getVisionScoreAdvantageLaneOpponent() < 0.05,
                "loser", !results.isWin()
        );
    }

    public PlayerResultsEntity mapToEntity(){
        return new PlayerResultsEntity(
                this.getKillParticipation(),
                this.getGoldAdvantageAt15(),
                this.getVisionScoreAdvantageLaneOpponent(),
                this.isWin()
        );
    }

}
