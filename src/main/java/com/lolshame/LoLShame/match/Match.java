package com.lolshame.LoLShame.match;

import com.lolshame.LoLShame.player.results.PlayerMatchDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matchId;

    private Long gameStartTimestamp;

    private Map<String, PlayerMatchDetails> playerStats;


}
