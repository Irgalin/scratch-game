package com.assignment.winlogic;

import com.assignment.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinCombinationEvaluator {

    public static Map<String, List<Config.WinCombination>> evaluate(String[][] matrix, Map<String, Config.WinCombination> winCombinations) {
        Map<String, List<Config.WinCombination>> winCombinationsBySymbolMap = new HashMap<>();
        WinCombinationContext context = new WinCombinationContext();

        for (Config.WinCombination combination : winCombinations.values()) {
            List<String> matchedSymbols = evaluateCombination(matrix, combination, context);
            addMatchedSymbolsToMap(matchedSymbols, combination, winCombinationsBySymbolMap);
        }

        return winCombinationsBySymbolMap;
    }

    private static List<String> evaluateCombination(String[][] matrix, Config.WinCombination combination, WinCombinationContext context) {
        context.setChecker(context.getChecker(combination.getWhen()));
        return context.executeStrategy(matrix, combination);
    }

    private static void addMatchedSymbolsToMap(List<String> matchedSymbols, Config.WinCombination combination, Map<String, List<Config.WinCombination>> winCombinationsBySymbolMap) {
        for (String symbol : matchedSymbols) {
            winCombinationsBySymbolMap
                    .computeIfAbsent(symbol, k -> new ArrayList<>())
                    .add(combination);
        }
    }

}