package com.lolshame.LoLShame.controller;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class ApiCallEntityListener {

    private static Clock clock;

    @Autowired
    public void setClock(Clock clock) {
        ApiCallEntityListener.clock = clock;
    }

    @PrePersist
    public void prePersist(ApiCallEntity entity) {
        if (entity.getTimestamp() == null) {
            entity.setTimestamp(LocalDateTime.now(clock));
        }
    }
}
