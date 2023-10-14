package com.lolshame.LoLShame.fetching;

import com.lolshame.LoLShame.player.results.PlayerResultsServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerResultsServiceTests {

    @Test
    public void SingleElementCollectorValidCase(){
        List<String> input = List.of("abc", "def", "fge");

        String expected = "abc";

        String actual = input.stream().filter(v -> (v.equals("abc"))).collect(PlayerResultsServiceImpl.singleElementCollector());

        assertEquals(expected, actual);
    }

    @Test
    public void SingleElementCollectorThrowsIllegalStateException(){

        List<String> input = List.of("abc", "def", "fge"); assertThrows(IllegalStateException.class,
                () -> {input.stream().collect(PlayerResultsServiceImpl.singleElementCollector());},
                "IllegalStateException expected");
    }
}
