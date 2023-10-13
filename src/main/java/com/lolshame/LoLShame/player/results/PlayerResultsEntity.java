package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.controller.ApiCallEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@EntityListeners(PlayerResultsEntityListener.class)
@NoArgsConstructor
@Data
@Entity
@Table(name = "player_results")
public class PlayerResultsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @CreatedDate
    private LocalDateTime timestamp;

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
