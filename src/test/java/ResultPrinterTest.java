import com.assignment.config.enums.ImpactType;
import com.assignment.result.GameResult;
import com.assignment.result.ResultPrinter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

public class ResultPrinterTest {

    @Test
    public void testPrintResultWithZeroReward() {
        GameResult result = Mockito.mock(GameResult.class);
        when(result.getMatrix()).thenReturn(new String[][]{
                {"E", "B", "MISS"},
                {"F", "D", "F"},
                {"A", "D", "E"}
        });
        when(result.getReward()).thenReturn(0.0);
        when(result.getAppliedWinningCombinations()).thenReturn(Collections.emptyMap());
        when(result.getAppliedBonusSymbol()).thenReturn("MISS");

        assertDoesNotThrow(() -> ResultPrinter.printResult(result));
    }

    @Test
    public void testPrintResultWithNonZeroRewardAndBonusSymbol() {
        GameResult result = Mockito.mock(GameResult.class);
        when(result.getMatrix()).thenReturn(new String[][]{
                {"E", "C", "F"},
                {"D", "+1000", "F"},
                {"D", "E", "B"}
        });
        when(result.getReward()).thenReturn(1000.0);
        Map<String, List<String>> winningCombinations = new HashMap<>();
        winningCombinations.put("D", List.of("same_symbol_3_times", "same_symbols_horizontally"));
        when(result.getAppliedWinningCombinations()).thenReturn(winningCombinations);
        when(result.getAppliedBonusSymbol()).thenReturn("+1000");

        assertDoesNotThrow(() -> ResultPrinter.printResult(result));
    }

    @Test
    public void testPrintResultWithNonZeroRewardAndMissBonusSymbol() {
        GameResult result = Mockito.mock(GameResult.class);
        when(result.getMatrix()).thenReturn(new String[][]{
                {"E", "C", "F"},
                {"D", "MISS", "F"},
                {"D", "E", "B"}
        });
        when(result.getReward()).thenReturn(1000.0);
        Map<String, List<String>> winningCombinations = new HashMap<>();
        winningCombinations.put("D", List.of("same_symbol_3_times", "same_symbols_horizontally"));
        when(result.getAppliedWinningCombinations()).thenReturn(winningCombinations);
        when(result.getAppliedBonusSymbol()).thenReturn(ImpactType.MISS.getValue());

        assertDoesNotThrow(() -> ResultPrinter.printResult(result));
    }

}