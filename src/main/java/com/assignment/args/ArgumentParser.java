package com.assignment.args;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgumentParser {

    public static ParsedArguments parseArguments(String[] args) throws ParseException {
        Options options = new Options();

        Option configOption = new Option("c", "config", true, "Configuration file path (must be a valid path to a JSON file)");
        configOption.setRequired(true);
        options.addOption(configOption);
        Option betAmountOption = new Option("b", "betting-amount", true, "Betting amount (must be a positive integer)");
        betAmountOption.setRequired(true);
        options.addOption(betAmountOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args, true);

            int betAmount = validateBettingAmount(cmd.getOptionValue("betting-amount"));
            String configFilePath = validateConfigFilePath(cmd.getOptionValue("config"));

            return new ParsedArguments(configFilePath, betAmount);

        } catch (ParseException e) {
            formatter.printHelp("The following arguments must be provided:", options);
            throw e;
        }
    }

    private static int validateBettingAmount(String betAmountStr) throws ParseException {
        int betAmountInt;
        try {
            betAmountInt = Integer.parseInt(betAmountStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Betting amount must be an integer.");
        }
        if (betAmountInt <= 0) {
            throw new ParseException("Betting amount must be greater than 0.");
        }
        return betAmountInt;
    }

    private static String validateConfigFilePath(String configFilePath) throws ParseException {
        Path path = Paths.get(configFilePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new ParseException("Config path must be a valid path to a file.");
        }
        if (!configFilePath.toLowerCase().endsWith(".json")) {
            throw new ParseException("Config must be a JSON file.");
        }
        return configFilePath;
    }

}