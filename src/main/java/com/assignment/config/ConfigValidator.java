package com.assignment.config;

import com.assignment.config.enums.SymbolType;
import com.assignment.config.enums.WinCombinationGroupType;

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
            List<String> probabilitiesErrorMessages = new ArrayList<>();
            validateProbabilities(config.getProbabilities(), probabilitiesErrorMessages);
            if (probabilitiesErrorMessages.isEmpty()) {
                validateRowsConsistency(config, errorMessages);
                validateColumnsConsistency(config, errorMessages);
            } else {
                errorMessages.addAll(probabilitiesErrorMessages);
            }
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

            if (symbol.getType() == null) {
                errorMessages.add("symbols[" + index + "].type is missing or empty.");
            }

            if (SymbolType.STANDARD.equals(symbol.getType())) {
                if (symbol.getRewardMultiplier() == null) {
                    errorMessages.add("symbols[" + index + "].reward_multiplier is missing.");
                } else if (symbol.getRewardMultiplier() <= 0) {
                    errorMessages.add("symbols[" + index + "].reward_multiplier must be greater than 0.");
                }
            } else if (SymbolType.BONUS.equals(symbol.getType())) {
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

                if (standardSymbol.getColumn() == null || standardSymbol.getColumn() < 0) {
                    errorMessages.add("probabilities.standard_symbols[" + i + "].column is invalid.");
                }
                if (standardSymbol.getRow() == null || standardSymbol.getRow() < 0) {
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

            if (winCombination.getGroup() == null) {
                errorMessages.add("win_combinations[" + index + "].group is missing or empty.");
            }

            if (winCombination.getWhen() == null) {
                errorMessages.add("win_combinations[" + index + "].when is missing or empty.");
            }

            if (WinCombinationGroupType.SAME_SYMBOLS.equals(winCombination.getGroup())) {
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

    private static void validateRowsConsistency(Config config, List<String> errorMessages) {
        int maxRowInProbabilities = config.getProbabilities().getStandardSymbols().stream()
                .mapToInt(Config.Probabilities.StandardSymbol::getRow)
                .max()
                .orElse(-1) + 1;
        Integer rowsInTopLevelProperty = config.getRows();
        if (rowsInTopLevelProperty != null) {
            if (!rowsInTopLevelProperty.equals(maxRowInProbabilities)) {
                errorMessages.add("'rows' property value doesn't match to the max number of rows in 'probabilities'");
            }
        } else {
            // 'rows' property is optional, so if it's not specified, take this value from 'probabilities'
            config.setRows(maxRowInProbabilities);
        }
    }

    private static void validateColumnsConsistency(Config config, List<String> errorMessages) {
        int maxColumnInProbabilities = config.getProbabilities().getStandardSymbols().stream()
                .mapToInt(Config.Probabilities.StandardSymbol::getColumn)
                .max()
                .orElse(-1) + 1;
        Integer columnsInTopLevelProperty = config.getColumns();
        if (columnsInTopLevelProperty != null) {
            if (!columnsInTopLevelProperty.equals(maxColumnInProbabilities)) {
                errorMessages.add("'columns' property value doesn't match to the max number of columns in 'probabilities'");
            }
        } else {
            // 'column' property is optional, so if it's not specified, take this value from 'probabilities'
            config.setColumns(maxColumnInProbabilities);
        }
    }

}