package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.caching.PlayerResultsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultsRepository extends JpaRepository<PlayerResultsEntity, String> {
    Optional<PlayerResultsEntity> findByPlayerId(String playerId);

    void deleteByPlayerId(String playerId);
}
