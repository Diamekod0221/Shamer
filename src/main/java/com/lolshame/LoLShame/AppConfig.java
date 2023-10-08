package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.results.ResultsCalculator;
import com.lolshame.LoLShame.player.results.ResultsCalculatorAveraged;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfig {

    @Bean
    public Clock getUTCClock(){
        return Clock.systemUTC();
    }

    @Bean
    public ResultsCalculator getCalculator(){return new ResultsCalculatorAveraged();
    }

}
