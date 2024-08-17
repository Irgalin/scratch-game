package com.assignment.result;

import com.assignment.config.Config;
import com.assignment.result.serializers.MatrixSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class GameResult {

    @JsonProperty("matrix")
    @JsonSerialize(using = MatrixSerializer.class)
    private String[][] matrix;

    @JsonProperty("reward")
    private double reward;

    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private String appliedBonusSymbol;

    public GameResult(String[][] matrix, double reward, Map<String, List<Config.WinCombination>> winCombBySymbolMap, String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = convertWinCombBySymbolMap(winCombBySymbolMap);
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public Map<String, List<String>> convertWinCombBySymbolMap(Map<String, List<Config.WinCombination>> winCombBySymbolMap) {
        Map<String, List<String>> convertedMap = new HashMap<>();

        for (Map.Entry<String, List<Config.WinCombination>> entry : winCombBySymbolMap.entrySet()) {
            String key = entry.getKey();
            List<String> names = entry.getValue().stream()
                    .map(Config.WinCombination::getName)
                    .collect(Collectors.toList());
            convertedMap.put(key, names);
        }

        return convertedMap;
    }


}