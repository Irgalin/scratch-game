package com.assignment.winlogic.checkers;

import com.assignment.config.Config.WinCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearSymbolsChecker implements WinCombinationChecker {

    @Override
    public List<String> check(String[][] matrix, WinCombination combination) {
        List<String> matchedSymbols = new ArrayList<>();

        for (List<String> area : combination.getCoveredAreas()) {
            Optional<String> matchedSymbol = getMatchedSymbol(matrix, area);
            matchedSymbol.ifPresent(matchedSymbols::add);
        }

        return matchedSymbols;
    }

    private Optional<String> getMatchedSymbol(String[][] matrix, List<String> area) {
        String firstSymbol = null;

        for (String position : area) {
            String[] parts = position.split(":");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            String symbol = matrix[row][col];

            if (firstSymbol == null) {
                firstSymbol = symbol;
            } else if (!symbol.equals(firstSymbol)) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(firstSymbol);
    }

}