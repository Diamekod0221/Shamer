package com.lolshame.LoLShame.player.results;

import java.util.List;

public class ResultsCalculatorAveraged implements ResultsCalculator{
    @Override
    public PlayerResults getResultsFromList(List<PlayerResults> resultList) {
        validateList(resultList);

        double averageKillParticipation = getAverageKillParticipation(resultList);

        double averageGoldAdvantageAt15 = getAverageGoldAdvantageAt15(resultList);

        long flooredAverageGold = getFloor(averageGoldAdvantageAt15);

        double averageVisionScoreAdvantageLaneOpponent = getAverageVisionScoreAdvantageLaneOpponent(resultList);

        boolean averageWin = isAverageWin(resultList);

        return PlayerResults.builder()
                .killParticipation(averageKillParticipation)
                .goldAdvantageAt15(flooredAverageGold)
                .visionScoreAdvantageLaneOpponent(averageVisionScoreAdvantageLaneOpponent)
                .win(averageWin)
                .build();
    }

    private static boolean isAverageWin(List<PlayerResults> resultList) {
        return resultList.stream()
                .map(PlayerResults::isWin)
                .filter(Boolean::booleanValue)
                .count() > (resultList.size() / 2);
    }

    private static double getAverageVisionScoreAdvantageLaneOpponent(List<PlayerResults> resultList) {
        return resultList.stream()
                .mapToDouble(PlayerResults::getVisionScoreAdvantageLaneOpponent)
                .average()
                .orElse(0.0);
    }

    private static long getFloor(double averageGoldAdvantageAt15) {
        return (long) Math.floor(averageGoldAdvantageAt15);
    }

    private static double getAverageGoldAdvantageAt15(List<PlayerResults> resultList) {
        return resultList.stream()
                .mapToLong(PlayerResults::getGoldAdvantageAt15)
                .average()
                .orElse(0);
    }

    private static double getAverageKillParticipation(List<PlayerResults> resultList) {
        return resultList.stream()
                .mapToDouble(PlayerResults::getKillParticipation)
                .average()
                .orElse(0.0);
    }

    private static void validateList(List<PlayerResults> resultList) {
        if (resultList == null || resultList.isEmpty()) {
            throw new IllegalArgumentException("ResultList is empty or null.");
        }
    }

}
