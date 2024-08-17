package com.assignment;

import com.assignment.args.ArgumentParser;
import com.assignment.args.ParsedArguments;
import com.assignment.config.Config;
import com.assignment.config.ConfigParser;
import com.assignment.config.ConfigValidator;
import com.assignment.matrix.RandomMatrixGenerator;
import com.assignment.matrix.RandomMatrixResult;
import com.assignment.result.GameResult;
import com.assignment.result.ResultPrinter;
import com.assignment.reward.RewardCalculator;
import com.assignment.winlogic.WinCombinationEvaluator;

import java.util.List;
import java.util.Map;

public class ScratchGameApplication {

    public static void main(String[] args) {
        try {
            ParsedArguments parsedArgs = ArgumentParser.parseArguments(args);
            Config config = ConfigParser.parseConfig(parsedArgs.configFilePath());
            ConfigValidator.validate(config);
            // Generate random matrix with standard symbols and a bonus symbol
            RandomMatrixResult matrixResult = RandomMatrixGenerator.generateMatrix(config);
            // Evaluate win combinations for each symbol
            Map<String, List<Config.WinCombination>> winCombBySymbolMap =
                    WinCombinationEvaluator.evaluate(matrixResult.getMatrix(), config.getWinCombinations());
            // Calculate reward based on win combinations and bonus symbol
            double reward = RewardCalculator.calculateReward(
                    winCombBySymbolMap,
                    matrixResult.getBonusSymbolValue(),
                    config.getSymbols(),
                    parsedArgs.bettingAmount());
            // Print the result
            ResultPrinter.printResult(new GameResult(
                    matrixResult.getMatrix(),
                    reward,
                    winCombBySymbolMap,
                    matrixResult.getBonusSymbolValue()));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}