package com.assignment.winlogic.checkers;

import com.assignment.config.Config.WinCombination;

import java.util.List;

public interface WinCombinationChecker {

    List<String> check(String[][] matrix, WinCombination combination);

}