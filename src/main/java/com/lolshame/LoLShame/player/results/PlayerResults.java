package com.lolshame.LoLShame.player.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlayerResults {
    private final double killParticipation;

    private final long goldAdvantageAt15;

    private final double visionScoreAdvantageLaneOpponent;

    private final boolean win;
}
