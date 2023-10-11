package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.TeamColorEnum;
import lombok.Getter;

@Getter
public final class CorruptMatchDetails extends PlayerMatchDetails{

    private final String corruptSubstring;

    public CorruptMatchDetails(String parsingSubstring){
        super(
                -1,
                -1,
                -1L,
                -1,
                false,
                PlayedLaneEnum.BOTTOM,
                TeamColorEnum.BLUE
        );
        this.corruptSubstring = parsingSubstring;
    }

}
