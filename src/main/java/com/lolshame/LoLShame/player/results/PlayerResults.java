package com.lolshame.LoLShame.player.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlayerResults {
    private double killParticipation;

    private long goldAdvantageAt15;

    private double visionScoreAdvantageLaneOpponent;

    private boolean win;
}
