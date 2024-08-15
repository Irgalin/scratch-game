package com.assignment;

import com.assignment.args.ArgumentParser;
import com.assignment.args.ParsedArguments;
import com.assignment.config.Config;
import com.assignment.config.ConfigParser;
import com.assignment.config.ConfigValidator;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class ScratchGameApplication {

    public static void main(String[] args) {
        try {
            ParsedArguments parsedArgs = ArgumentParser.parseArguments(args);
            System.out.println("Config file path: " + parsedArgs.configFilePath());
            System.out.println("Betting amount: " + parsedArgs.bettingAmount());

            Config config = ConfigParser.parseConfig(parsedArgs.configFilePath());
            ConfigValidator.validate(config);
            System.out.println("Config file parsed successfully.");

        } catch (ParseException | IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

}