package com.assignment.winlogic;

import com.assignment.config.Config;
import com.assignment.config.enums.WinCombinationWhenType;
import com.assignment.winlogic.checkers.LinearSymbolsChecker;
import com.assignment.winlogic.checkers.SameSymbolsChecker;
import com.assignment.winlogic.checkers.WinCombinationChecker;
import lombok.Setter;

import java.util.List;

@Setter
public class WinCombinationContext {

    private WinCombinationChecker checker;

    public List<String> executeStrategy(String[][] matrix, Config.WinCombination combination) {
        return checker.check(matrix, combination);
    }

    public WinCombinationChecker getChecker(WinCombinationWhenType whenType) {
        return switch (whenType) {
            case SAME_SYMBOLS -> new SameSymbolsChecker();
            case LINEAR_SYMBOLS -> new LinearSymbolsChecker();
        };
    }

}