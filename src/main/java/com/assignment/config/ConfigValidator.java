package com.assignment.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigValidator {

    public static void validate(Config config) throws InvalidConfigException {
        List<String> errorMessages = new ArrayList<>();

        validateTopLevelProperties(config, errorMessages);

        if (config.getSymbols() != null) {
            validateSymbols(config.getSymbols(), errorMessages);
        }
        if (config.getProbabilities() != null) {
            validateProbabilities(config.getProbabilities(), errorMessages);
        }
        if (config.getWinCombinations() != null) {
            validateWinCombinations(config.getWinCombinations(), errorMessages);
        }

        if (!errorMessages.isEmpty()) {
            throw new InvalidConfigException(errorMessages);
        }
    }

    private static void validateTopLevelProperties(Config config, List<String> errorMessages) {
        if (config.getSymbols() == null || config.getSymbols().isEmpty()) {
            errorMessages.add("Symbols are missing or empty.");
        }
        if (config.getProbabilities() == null) {
            errorMessages.add("Probabilities are missing.");
        }
        if (config.getWinCombinations() == null || config.getWinCombinations().isEmpty()) {
            errorMessages.add("Win combinations are missing or empty.");
        }
    }

    private static void validateSymbols(Map<String, Config.Symbol> symbols, List<String> errorMessages) {
        int index = 0;
        for (Map.Entry<String, Config.Symbol> entry : symbols.entrySet()) {
            Config.Symbol symbol = entry.getValue();

            if (symbol.getType() == null || symbol.getType().isEmpty()) {
                errorMessages.add("symbols[" + index + "].type is missing or empty.");
            }

            if ("standard".equals(symbol.getType())) {
                if (symbol.getRewardMultiplier() == null) {
                    errorMessages.add("symbols[" + index + "].reward_multiplier is missing.");
                } else if (symbol.getRewardMultiplier() <= 0) {
                    errorMessages.add("symbols[" + index + "].reward_multiplier must be greater than 0.");
                }
            } else if ("bonus".equals(symbol.getType())) {
                if (symbol.getImpact() == null || symbol.getImpact().isEmpty()) {
                    errorMessages.add("symbols[" + index + "].impact is missing or empty.");
                }
            }
            index++;
        }
    }

    private static void validateProbabilities(Config.Probabilities probabilities, List<String> errorMessages) {
        if (probabilities.getStandardSymbols() != null) {
            for (int i = 0; i < probabilities.getStandardSymbols().size(); i++) {
                Config.Probabilities.StandardSymbol standardSymbol = probabilities.getStandardSymbols().get(i);

                if (standardSymbol.getColumn() < 0) {
                    errorMessages.add("probabilities.standard_symbols[" + i + "].column is invalid.");
                }
                if (standardSymbol.getRow() < 0) {
                    errorMessages.add("probabilities.standard_symbols[" + i + "].row is invalid.");
                }
                if (standardSymbol.getSymbols() == null || standardSymbol.getSymbols().isEmpty()) {
                    errorMessages.add("probabilities.standard_symbols[" + i + "].symbols are missing or empty.");
                }
            }
        }

        if (probabilities.getBonusSymbols() != null) {
            if (probabilities.getBonusSymbols().getSymbols() == null || probabilities.getBonusSymbols().getSymbols().isEmpty()) {
                errorMessages.add("probabilities.bonus_symbols.symbols are missing or empty.");
            }
        }
    }

    private static void validateWinCombinations(Map<String, Config.WinCombination> winCombinations, List<String> errorMessages) {
        int index = 0;
        for (Map.Entry<String, Config.WinCombination> entry : winCombinations.entrySet()) {
            Config.WinCombination winCombination = entry.getValue();

            if (winCombination.getGroup() == null || winCombination.getGroup().isEmpty()) {
                errorMessages.add("win_combinations[" + index + "].group is missing or empty.");
            }

            if (winCombination.getWhen() == null || winCombination.getWhen().isEmpty()) {
                errorMessages.add("win_combinations[" + index + "].when is missing or empty.");
            }

            if ("same_symbols".equals(winCombination.getGroup())) {
                if (winCombination.getRewardMultiplier() == null) {
                    errorMessages.add("win_combinations[" + index + "].reward_multiplier is missing.");
                }
                if (winCombination.getRewardMultiplier() <= 0) {
                    errorMessages.add("win_combinations[" + index + "].reward_multiplier must be greater than 0.");
                }
                if (winCombination.getCount() <= 0) {
                    errorMessages.add("win_combinations[" + index + "].count must be greater than 0.");
                }
            } else {
                if (winCombination.getCoveredAreas() == null || winCombination.getCoveredAreas().isEmpty()) {
                    errorMessages.add("win_combinations[" + index + "].covered_areas are missing or empty.");
                }
            }
            index++;
        }
    }
}