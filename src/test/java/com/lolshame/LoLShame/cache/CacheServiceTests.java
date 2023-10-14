package com.lolshame.LoLShame.cache;

import com.lolshame.LoLShame.caching.CacheService;
import com.lolshame.LoLShame.caching.CacheServiceImpl;
import com.lolshame.LoLShame.caching.ApiCallEntity;
import com.lolshame.LoLShame.caching.PlayerResultsEntity;
import com.lolshame.LoLShame.player.results.ResultsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheServiceTests {

    private CacheService cacheService;

    @Mock
    private ResultsRepository repository;

    @Mock
    private Clock clock;

    @Mock
    private ApiCallEntity testCall;

    @Mock
    private PlayerResultsEntity testPlayer;

    @BeforeEach
    public void setUp(){
        this.cacheService = new CacheServiceImpl(repository, clock);


        repository.save(testPlayer);

    }

    @Test
    public void testFetchingLogic(){

    }



}
