package com.assignment.reward;

import com.assignment.config.Config;

import java.util.List;
import java.util.Map;

public class RewardCalculator {

    public static double calculateReward(Map<String, List<Config.WinCombination>> winCombBySymbolMap,
                                         String bonusSymbolValue, Map<String, Config.Symbol> allSymbols,
                                         double bettingAmount) {
        if (winCombBySymbolMap == null || winCombBySymbolMap.isEmpty()) {
            return 0.0;
        }
        double totalReward = 0;
        double bonusMultiplier = 1;
        double extraBonus = 0;

        for (Map.Entry<String, List<Config.WinCombination>> entry : winCombBySymbolMap.entrySet()) {
            String symbol = entry.getKey();
            List<Config.WinCombination> winCombinations = entry.getValue();
            Config.Symbol symbolConfig = allSymbols.get(symbol);

            double symbolReward = bettingAmount * symbolConfig.getRewardMultiplier();
            double winMultiplier = calculateWinMultiplier(winCombinations);

            totalReward += symbolReward * winMultiplier;
        }

        Config.Symbol bonusSymbol = allSymbols.get(bonusSymbolValue);

        if (bonusSymbol != null && bonusSymbol.getImpact() != null) {
            switch (bonusSymbol.getImpact()) {
                case MULTIPLY_REWARD:
                    bonusMultiplier *= bonusSymbol.getRewardMultiplier();
                    break;
                case EXTRA_BONUS:
                    extraBonus += bonusSymbol.getExtra();
                    break;
                case MISS:
                    break;
            }
        }

        return totalReward * bonusMultiplier + extraBonus;
    }

    private static double calculateWinMultiplier(List<Config.WinCombination> winCombinations) {
        return winCombinations.stream()
                .mapToDouble(Config.WinCombination::getRewardMultiplier)
                .reduce(1, (a, b) -> a * b);
    }

}