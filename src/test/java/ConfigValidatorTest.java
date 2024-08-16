
import com.assignment.config.Config;
import com.assignment.config.ConfigValidator;
import com.assignment.config.InvalidConfigException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigValidatorTest {

    @Test
    void testValidConfig() {
        Config config = createValidConfig();
        assertDoesNotThrow(() -> ConfigValidator.validate(config));
    }

    @Test
    void testMissingSymbols() {
        Config config = createValidConfig();
        config.setSymbols(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("Symbols are missing or empty."));
    }

    @Test
    void testMissingProbabilities() {
        Config config = createValidConfig();
        config.setProbabilities(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("Probabilities are missing."));
    }

    @Test
    void testMissingWinCombinations() {
        Config config = createValidConfig();
        config.setWinCombinations(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("Win combinations are missing or empty."));
    }

    @Test
    void testInvalidSymbols() {
        Config config = createValidConfig();
        config.getSymbols().get("symbol1").setType(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("symbols[0].type is missing or empty."));
    }

    @Test
    void testInvalidSymbolsRewardMultiplier() {
        Config config = createValidConfig();
        config.getSymbols().get("symbol1").setRewardMultiplier(0.0);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("symbols[0].reward_multiplier must be greater than 0."));
    }

    @Test
    void testInvalidProbabilities() {
        Config config = createValidConfig();
        config.getProbabilities().getStandardSymbols().get(0).setColumn(-1);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("probabilities.standard_symbols[0].column is invalid."));
    }

    @Test
    void testInvalidProbabilitiesRow() {
        Config config = createValidConfig();
        config.getProbabilities().getStandardSymbols().get(0).setRow(-1);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("probabilities.standard_symbols[0].row is invalid."));
    }

    @Test
    void testInvalidWinCombinations() {
        Config config = createValidConfig();
        config.getWinCombinations().get("win1").setGroup(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("win_combinations[0].group is missing or empty."));
    }

    @Test
    void testMissingWhenInWinCombinations() {
        Config config = createValidConfig();
        config.getWinCombinations().get("win1").setWhen(null);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("win_combinations[0].when is missing or empty."));
    }

    @Test
    void testInvalidWinCombinationsRewardMultiplier() {
        Config config = createValidConfig();
        config.getWinCombinations().get("win1").setRewardMultiplier(0.0);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("win_combinations[0].reward_multiplier must be greater than 0."));
    }

    @Test
    void testInvalidWinCombinationsCount() {
        Config config = createValidConfig();
        config.getWinCombinations().get("win1").setCount(0);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("win_combinations[0].count must be greater than 0."));
    }

    @Test
    void testInvalidBonusSymbolImpact() {
        Config config = createValidConfig();
        Config.Symbol bonusSymbol = new Config.Symbol();
        bonusSymbol.setType("bonus");
        bonusSymbol.setImpact(null);
        config.getSymbols().put("symbol2", bonusSymbol);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("symbols[1].impact is missing or empty."));
    }

    @Test
    void testInvalidSameSymbolsWinCombination() {
        Config config = createValidConfig();
        Config.WinCombination winCombination = config.getWinCombinations().get("win1");
        winCombination.setRewardMultiplier(0.0);
        winCombination.setCount(0);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("win_combinations[0].reward_multiplier must be greater than 0."));
        assertTrue(exception.getMessage().contains("win_combinations[0].count must be greater than 0."));
    }


    @Test
    void testRowsConsistency() {
        Config config = createValidConfig();
        // Set rows to a value that does not match probabilities
        config.setRows(2);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("'rows' property value doesn't match to the max number of rows in 'probabilities'"));
    }

    @Test
    void testColumnsConsistency() {
        Config config = createValidConfig();
        // Set columns to a value that does not match probabilities
        config.setColumns(2);
        InvalidConfigException exception = assertThrows(InvalidConfigException.class, () -> ConfigValidator.validate(config));
        assertTrue(exception.getMessage().contains("'columns' property value doesn't match to the max number of columns in 'probabilities'"));
    }

    @Test
    void testSettingRowsFromProbabilities() {
        Config config = createValidConfig();
        config.setRows(null);
        assertDoesNotThrow(() -> ConfigValidator.validate(config));
        // Check if rows is set from probabilities
        assertEquals(1, config.getRows());
    }

    @Test
    void testSettingColumnsFromProbabilities() {
        Config config = createValidConfig();
        config.setColumns(null);
        assertDoesNotThrow(() -> ConfigValidator.validate(config));
        // Check if columns is set from probabilities
        assertEquals(1, config.getColumns());
    }


    private Config createValidConfig() {
        Config config = new Config();

        Map<String, Config.Symbol> symbols = new HashMap<>();
        Config.Symbol symbol = new Config.Symbol();
        symbol.setType("standard");
        symbol.setRewardMultiplier(2.0);
        symbols.put("symbol1", symbol);
        config.setSymbols(symbols);

        Config.Probabilities probabilities = new Config.Probabilities();
        Config.Probabilities.StandardSymbol standardSymbol = new Config.Probabilities.StandardSymbol();
        standardSymbol.setColumn(0);
        standardSymbol.setRow(0);
        standardSymbol.setSymbols(Map.of("symbol1", 100));
        probabilities.setStandardSymbols(List.of(standardSymbol));
        config.setProbabilities(probabilities);

        Map<String, Config.WinCombination> winCombinations = new HashMap<>();
        Config.WinCombination winCombination = new Config.WinCombination();
        winCombination.setGroup("same_symbols");
        winCombination.setRewardMultiplier(3.0);
        winCombination.setCount(3);
        winCombination.setWhen("some_condition");
        winCombinations.put("win1", winCombination);
        config.setWinCombinations(winCombinations);

        return config;
    }
}