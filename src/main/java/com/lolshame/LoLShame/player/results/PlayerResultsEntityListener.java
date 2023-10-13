package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.controller.ApiCallEntity;
import com.lolshame.LoLShame.controller.ApiCallEntityListener;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class PlayerResultsEntityListener {

    private static Clock clock;

    @Autowired
    public void setClock(Clock clock) {PlayerResultsEntityListener.clock = clock;
    }

    @PrePersist
    public void prePersist(PlayerResultsEntity entity) {
        if (entity.getTimestamp() == null) {
            entity.setTimestamp(LocalDateTime.now(clock));
        }
    }

}
