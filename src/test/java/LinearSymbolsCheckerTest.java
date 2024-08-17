import com.assignment.config.Config;
import com.assignment.winlogic.checkers.LinearSymbolsChecker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearSymbolsCheckerTest {

    private final LinearSymbolsChecker checker = new LinearSymbolsChecker();

    @Test
    public void testEmptyMatrix() {
        String[][] matrix = new String[0][0];
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(Collections.emptyList());
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSingleSymbolMatrix() {
        String[][] matrix = {{"A"}};
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(Collections.singletonList(Collections.singletonList("0:0")));
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.singletonList("A"), result);
    }

    @Test
    public void testNoMatchingAreas() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"D", "E", "F"},
                {"G", "H", "I"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(Arrays.asList(
                Arrays.asList("0:0", "0:1", "0:2"),
                Arrays.asList("1:0", "1:1", "1:2"),
                Arrays.asList("2:0", "2:1", "2:2")
        ));
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSomeMatchingAreas() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "A", "C"},
                {"A", "A", "B"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(Arrays.asList(
                Arrays.asList("0:0", "0:1", "0:2"),
                Arrays.asList("1:0", "1:1", "1:2"),
                Arrays.asList("2:0", "2:1", "2:2")
        ));
        List<String> result = checker.check(matrix, combination);
        assertEquals(List.of("A"), result);
    }

    @Test
    public void testAllSymbolsMatchInArea() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(List.of(
                Arrays.asList("0:0", "0:1", "0:2", "1:0", "1:1", "1:2", "2:0", "2:1", "2:2")
        ));
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.singletonList("A"), result);
    }

    @Test
    public void testNoSymbolsMatchInArea() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"D", "E", "F"},
                {"G", "H", "I"}
        };
        Config.WinCombination combination = new Config.WinCombination();
        combination.setCoveredAreas(List.of(
                Arrays.asList("0:0", "0:1", "0:2", "1:0", "1:1", "1:2", "2:0", "2:1", "2:2")
        ));
        List<String> result = checker.check(matrix, combination);
        assertEquals(Collections.emptyList(), result);
    }

}