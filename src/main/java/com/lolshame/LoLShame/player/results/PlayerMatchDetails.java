package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.TeamColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMatchDetails {

    private int perfectGame;

    private double killParticipation;

    private long goldEarned;

    private double visionScoreAdvantageLaneOpponent;

    private boolean win;

    private PlayedLaneEnum lane;

    private TeamColor team;

}


