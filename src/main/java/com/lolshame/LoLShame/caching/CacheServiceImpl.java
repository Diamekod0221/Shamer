package com.lolshame.LoLShame.caching;

import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.ResultsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CacheServiceImpl implements CacheService {

    @Autowired
    private ResultsRepository repository;

    @Autowired
    private Clock clock;

    @Override
    public boolean checkIfCanFetch(ApiCallEntity apiCall) {
        String id = apiCall.getSummonerId();
        return checkIfSaved(id) && !checkIfOutdated(id);
    }

    private boolean checkIfSaved(String id){
        return (repository.findByPlayerId(id).isPresent());
    }

    private boolean checkIfOutdated(String id){
        Optional<PlayerResultsEntity> results = repository.findByPlayerId(id);
        if(results.isPresent()){
            boolean isOutdated = results.get().getTimestamp().isBefore(LocalDateTime.now(clock).minusDays(7L));
            if(isOutdated){
                repository.deleteByPlayerId(id);
                return true;
            }
            else{
                return false;
            }
        }
        log.warn("Didn't find record: " + id + " in DB while checking if outdated.");
        return true;
    }

    @Override
    public PlayerResults fetchSummonerFromDB(ApiCallEntity apiCall) {
        Optional<PlayerResultsEntity> fetched = repository.findByPlayerId(apiCall.getSummonerId());
        return (fetched.map(PlayerResultsEntity::mapToResults).orElse(null));
    }

    @Override
    public void saveResults(PlayerResults results, String playerId) {
        PlayerResultsEntity entity = results.mapToEntity();
        entity.setPlayerId(playerId);
        this.saveResults(entity);
    }

    @Override
    public void saveResults(PlayerResultsEntity results) {
        repository.save(results);
    }
    @Override
    public List<PlayerResults> fetchResultsList() {
        return repository.findAll().stream().map(PlayerResultsEntity::mapToResults).toList();
    }

    @Override
    public void deleteResultsById(String resultsId) {
        repository.deleteById(resultsId);
    }


}
