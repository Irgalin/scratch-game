package com.assignment;

import com.assignment.args.ArgumentParser;
import com.assignment.args.ParsedArguments;
import com.assignment.config.Config;
import com.assignment.config.ConfigParser;
import com.assignment.config.ConfigValidator;
import com.assignment.matrix.RandomMatrixGenerator;
import com.assignment.matrix.RandomMatrixResult;
import com.assignment.reward.RewardCalculator;
import com.assignment.winlogic.WinCombinationEvaluator;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScratchGameApplication {

    public static void main(String[] args) {
        try {
            ParsedArguments parsedArgs = ArgumentParser.parseArguments(args);
            System.out.println("Config file path: " + parsedArgs.configFilePath());
            System.out.println("Betting amount: " + parsedArgs.bettingAmount());

            Config config = ConfigParser.parseConfig(parsedArgs.configFilePath());
            ConfigValidator.validate(config);
            System.out.println("Config file parsed successfully.");

            RandomMatrixResult matrixResult = RandomMatrixGenerator.generateMatrix(config);
            System.out.println("Generated matrix:");
            for (String[] row : matrixResult.getMatrix()) {
                System.out.println(Arrays.toString(row));
            }
            Map<String, List<Config.WinCombination>> winCombBySymbolMap =
                    WinCombinationEvaluator.evaluate(matrixResult.getMatrix(), config.getWinCombinations());

            printWinCombosMap(winCombBySymbolMap);

            double reward = RewardCalculator.calculateReward(
                    winCombBySymbolMap,
                    matrixResult.getBonusSymbolValue(),
                    config.getSymbols(),
                    parsedArgs.bettingAmount());

            System.out.println("Reward: " + reward);


        } catch (ParseException | IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    private static void printWinCombosMap(Map<String, List<Config.WinCombination>> winCombosMap) {
        System.out.println("Win Combinations by Symbol:");
        winCombosMap.forEach((symbol, winCombinations) -> {
            System.out.println("Symbol: " + symbol);
            winCombinations.forEach(winCombination -> System.out.println("  Win Combination: " + winCombination));
        });
    }

}