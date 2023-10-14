package com.lolshame.LoLShame.caching;

import com.lolshame.LoLShame.player.results.PlayerResults;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@EntityListeners(PlayerResultsEntityListener.class)
@NoArgsConstructor
@Data
@Entity
@Table(name = "player_results", uniqueConstraints = @UniqueConstraint(columnNames = {"playerId"}))
public class PlayerResultsEntity {

    @Id
    @UuidGenerator
    private String id;

    @CreatedDate
    private LocalDateTime timestamp;

    private String playerId;

    private double killParticipation;

    private long goldAdvantageAt15;

    private double visionScoreAdvantageLaneOpponent;

    private boolean win;

    public PlayerResultsEntity(double killParticipation, long goldAdvantageAt15, double visionScoreAdvantageLaneOpponent, boolean win) {
        this.killParticipation = killParticipation;
        this.goldAdvantageAt15 = goldAdvantageAt15;
        this.visionScoreAdvantageLaneOpponent = visionScoreAdvantageLaneOpponent;
        this.win = win;
    }

    public PlayerResults mapToResults(){
        return new PlayerResults(
                this.getKillParticipation(),
                this.getGoldAdvantageAt15(),
                this.getVisionScoreAdvantageLaneOpponent(),
                this.isWin()
        );
    }


}
