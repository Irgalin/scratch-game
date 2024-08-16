import com.assignment.config.Config;
import com.assignment.config.Config.Probabilities;
import com.assignment.config.Config.Probabilities.StandardSymbol;
import com.assignment.matrix.RandomMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RandomMatrixGeneratorTest {

    @Test
    void testValidMatrixGeneration() {
        Config config = createValidConfig();
        String[][] matrix = RandomMatrixGenerator.generateMatrix(config);
        assertNotNull(matrix);
        assertEquals(config.getRows(), matrix.length);
        assertEquals(config.getColumns(), matrix[0].length);
    }

    @Test
    void testRandomSymbolSelection() {
        Config config = createValidConfig();
        String[][] matrix = RandomMatrixGenerator.generateMatrix(config);
        assertNotNull(matrix);
        assertTrue(matrix.length > 0 && matrix[0].length > 0);
        for (String[] row : matrix) {
            for (String cell : row) {
                assertNotNull(cell);
            }
        }
    }

    private Config createValidConfig() {
        Config config = new Config();
        config.setRows(3);
        config.setColumns(3);

        Probabilities probabilities = new Probabilities();
        Map<String, Integer> symbols = new HashMap<>();
        symbols.put("A", 1);
        symbols.put("B", 2);
        symbols.put("C", 3);

        List<StandardSymbol> standardSymbols = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                StandardSymbol standardSymbol = new StandardSymbol();
                standardSymbol.setRow(row);
                standardSymbol.setColumn(col);
                standardSymbol.setSymbols(new HashMap<>(symbols));
                standardSymbols.add(standardSymbol);
            }
        }
        probabilities.setStandardSymbols(standardSymbols);

        // Initialize BonusSymbols
        Map<String, Integer> bonusSymbolsMap = new HashMap<>();
        bonusSymbolsMap.put("X", 5);
        bonusSymbolsMap.put("Y", 10);
        Probabilities.BonusSymbols bonusSymbols = new Probabilities.BonusSymbols();
        bonusSymbols.setSymbols(bonusSymbolsMap);
        probabilities.setBonusSymbols(bonusSymbols);

        config.setProbabilities(probabilities);

        return config;
    }

}