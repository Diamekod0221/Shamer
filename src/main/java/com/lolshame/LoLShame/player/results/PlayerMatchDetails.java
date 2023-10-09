package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.TeamColorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlayerMatchDetails {

    private int perfectGame;

    private double killParticipation;

    private long goldEarned;

    private double visionScoreAdvantageLaneOpponent;

    private boolean win;

    private PlayedLaneEnum lane;

    private TeamColorEnum team;

}


