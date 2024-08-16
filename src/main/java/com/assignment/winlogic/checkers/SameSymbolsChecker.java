package com.assignment.winlogic.checkers;

import com.assignment.config.Config.WinCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class SameSymbolsChecker implements WinCombinationChecker {

    @Override
    public List<String> check(String[][] matrix, WinCombination combination) {
        List<String> matchedSymbols = new ArrayList<>();
        Map<String, Integer> symbolCounts = countSymbols(matrix);

        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            if (Objects.equals(entry.getValue(), combination.getCount())) {
                matchedSymbols.add(entry.getKey());
            }
        }
        return matchedSymbols;
    }

    private Map<String, Integer> countSymbols(String[][] matrix) {
        Map<String, Integer> symbolCounts = new HashMap<>();
        for (String[] row : matrix) {
            for (String cell : row) {
                symbolCounts.put(cell, symbolCounts.getOrDefault(cell, 0) + 1);
            }
        }
        return symbolCounts;
    }

}