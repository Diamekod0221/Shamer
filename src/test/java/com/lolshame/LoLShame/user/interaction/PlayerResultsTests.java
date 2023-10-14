package com.lolshame.LoLShame.user.interaction;

import com.lolshame.LoLShame.player.Player;
import com.lolshame.LoLShame.player.results.PlayerResults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerResultsTests {

    public static final PlayerResults shamingResults = constructTestResults(ResultsTypeEnum.SHAMING);
    public static final PlayerResults cleanResults = constructTestResults(ResultsTypeEnum.CLEAN);

    private static final Map<String, Boolean> booleanizedCleanResults = constructBooleanizedResults(ResultsTypeEnum.CLEAN);

    private static final Map<String, Boolean> booleanizedShamingResults = constructBooleanizedResults(ResultsTypeEnum.SHAMING) ;

    private static PlayerResults constructTestResults(ResultsTypeEnum typeEnum){
        return PlayerResults.builder()
                .killParticipation(0.7)
                .goldAdvantageAt15(350)
                .visionScoreAdvantageLaneOpponent(0)
                .win((typeEnum == ResultsTypeEnum.CLEAN))
                .build();
    }

    private static Map<String, Boolean> constructBooleanizedResults(ResultsTypeEnum typeEnum){
        return Map.of(
                "alone", false,
                "poor", false,
                "blind", true,
                "loser", (typeEnum == ResultsTypeEnum.SHAMING)
        );
    }
    @Test
    public void booleanizeShamingResults(){
        Map<String, Boolean> actual = PlayerResults.booleanizeResults(shamingResults);

        assertEquals(booleanizedShamingResults, actual);
    }

    @Test
    public void booleanizeCleanResults(){
        Map<String, Boolean> actual = PlayerResults.booleanizeResults(cleanResults);

        assertEquals(booleanizedCleanResults, actual);
    }

    @Test
    public void areShamingResultsShaming(){
        assertTrue(shamingResults.areShamingResults());
    }

    @Test
    public void areShamingResultsClean(){
        assertFalse(cleanResults.areShamingResults());
    }



}
