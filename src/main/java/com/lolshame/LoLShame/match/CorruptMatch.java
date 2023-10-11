package com.lolshame.LoLShame.match;

import java.util.Collections;

public final class CorruptMatch extends Match{

    public CorruptMatch(String matchId){
        super(
                -1L,
                matchId,
                -1L,
                Collections.emptyMap()             
        );
    }
}
