import com.assignment.config.Config;
import com.assignment.winlogic.checkers.SameSymbolsChecker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SameSymbolsCheckerTest {

    private final SameSymbolsChecker checker = new SameSymbolsChecker();

    @Test
    public void testEmptyMatrix() {
        String[][] matrix = new String[0][0];
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(0);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSingleSymbolMatrix() {
        String[][] matrix = {{"A"}};
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(1);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.singletonList("A"), result);
    }

    @Test
    public void testNoMatchingCounts() {
        String[][] matrix = {
                {"A", "B"},
                {"C", "D"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(3);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSomeMatchingCounts() {
        String[][] matrix = {
                {"A", "A"},
                {"B", "B"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(2);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Arrays.asList("A", "B"), result);
    }

    @Test
    public void testAllSymbolsMatchCount() {
        String[][] matrix = {
                {"A", "A"},
                {"A", "A"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(4);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.singletonList("A"), result);
    }

    @Test
    public void testNoSymbolsMatchCount() {
        String[][] matrix = {
                {"A", "B"},
                {"C", "D"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCount(4);
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

}