package com.assignment.matrix;

import com.assignment.config.Config;
import com.assignment.config.Config.Probabilities.StandardSymbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomMatrixGenerator {

    public static String[][] generateMatrix(Config config) {
        int rows = config.getRows();
        int columns = config.getColumns();
        String[][] resultMatrix = new String[rows][columns];
        Random random = new Random();

        Map<String, Map<String, Integer>> standardSymbolsMap = createSymbolMap(config);
        Map<String, Integer> bonusSymbolsMap = config.getProbabilities().getBonusSymbols().getSymbols();

        BonusSymbolCell bonusSymbolCell = calculateBonusSymbolCell(rows, columns, random, standardSymbolsMap);
        populateMatrix(resultMatrix, rows, columns, standardSymbolsMap, bonusSymbolsMap, bonusSymbolCell, random);

        return resultMatrix;
    }

    private static Map<String, Map<String, Integer>> createSymbolMap(Config config) {
        Map<String, Map<String, Integer>> symbolMap = new HashMap<>();
        for (StandardSymbol probability : config.getProbabilities().getStandardSymbols()) {
            String key = generateKey(probability.getRow(), probability.getColumn());
            symbolMap.put(key, probability.getSymbols());
        }
        return symbolMap;
    }

    private static String getRandomSymbol(Map<String, Integer> symbolProbabilities, Random random) {
        int totalWeight = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }
        throw new MatrixGenerationException("Failed to calculate a random symbol");
    }

    private static int calculateTotalProbability(Map<String, Map<String, Integer>> symbolMap) {
        return symbolMap.values().stream()
                .flatMap(map -> map.values().stream())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static BonusSymbolCell calculateBonusSymbolCell(int rows, int columns, Random random, Map<String, Map<String, Integer>> symbolMap) {
        int totalProbability = calculateTotalProbability(symbolMap);
        int randomValue = random.nextInt(totalProbability);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                String key = generateKey(row, col);
                int cellProbability = symbolMap.get(key).values().stream().mapToInt(Integer::intValue).sum();
                randomValue -= cellProbability;
                if (randomValue < 0) {
                    return new BonusSymbolCell(row, col);
                }
            }
        }
        throw new MatrixGenerationException("Failed to calculate a bonus symbol cell");
    }

    private static void populateMatrix(String[][] matrix, int rows, int columns, Map<String, Map<String, Integer>> symbolMap, Map<String, Integer> bonusSymbols, BonusSymbolCell bonusSymbolCell, Random random) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row == bonusSymbolCell.row() && col == bonusSymbolCell.column()) {
                    matrix[row][col] = getRandomSymbol(bonusSymbols, random);
                } else {
                    String key = generateKey(row, col);
                    Map<String, Integer> symbolProbabilities = symbolMap.get(key);
                    matrix[row][col] = getRandomSymbol(symbolProbabilities, random);
                }
            }
        }
    }

    private static String generateKey(int row, int column) {
        return row + "-" + column;
    }

}