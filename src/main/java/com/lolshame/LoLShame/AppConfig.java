package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.results.ResultsCalculator;
import com.lolshame.LoLShame.player.results.ResultsCalculatorAveraged;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Clock;

@Configuration
public class AppConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/shamer");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("R2sHT3PkMrq7nN1SbvRj");
        return dataSourceBuilder.build();
    }


    @Bean
    public Clock getUTCClock() {
        return Clock.systemUTC();
    }

    @Bean
    public ResultsCalculator getCalculator() {
        return new ResultsCalculatorAveraged();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }

}
