package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.results.PlayerResultsService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerResultsServiceTests {

    @Test
    public void SingleElementCollectorValidCase(){
        List<String> input = List.of("abc", "def", "fge");

        String expected = "abc";

        String actual = input.stream().filter(v -> (v.equals("abc"))).collect(PlayerResultsService.singleElementCollector());

        assertEquals(expected, actual);
    }

    @Test
    public void SingleElementCollectorThrowsIllegalStateException(){

        List<String> input = List.of("abc", "def", "fge"); assertThrows(IllegalStateException.class,
                () -> {input.stream().collect(PlayerResultsService.singleElementCollector());},
                "IllegalStateException expected");
    }
}
