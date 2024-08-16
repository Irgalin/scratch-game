import com.assignment.config.Config;
import com.assignment.config.enums.ImpactType;
import com.assignment.reward.RewardCalculator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardCalculatorTest {

    @Test
    public void testCalculateRewardNoWinningCombinations() {
        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Collections.emptyMap();
        String bonusSymbolValue = "";
        Map<String, Config.Symbol> symbols = Collections.emptyMap();
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(0, reward);
    }

    @Test
    public void testCalculateRewardMultipleWinningCombinations() {
        Config.Symbol symbolConfig = new Config.Symbol();
        symbolConfig.setRewardMultiplier(2.0);

        Config.WinCombination winCombination = new Config.WinCombination();
        winCombination.setRewardMultiplier(1.5);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of("A", List.of(winCombination));
        String bonusSymbolValue = "";
        Map<String, Config.Symbol> symbols = Map.of("A", symbolConfig);
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(30.0, reward);
    }

    @Test
    public void testCalculateRewardWithBonusSymbols() {
        Config.Symbol symbolConfig = new Config.Symbol();
        symbolConfig.setRewardMultiplier(2.0);
        symbolConfig.setImpact(ImpactType.MULTIPLY_REWARD);

        Config.WinCombination winCombination = new Config.WinCombination();
        winCombination.setRewardMultiplier(1.5);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of("A", List.of(winCombination));
        String bonusSymbolValue = "A";
        Map<String, Config.Symbol> symbols = Map.of("A", symbolConfig);
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(60.0, reward);
    }

    @Test
    public void testCalculateRewardCombinationOfWinningAndExtraBonusSymbols() {
        Config.Symbol symbolConfigA = new Config.Symbol();
        symbolConfigA.setRewardMultiplier(2.0);

        Config.Symbol symbolConfigB = new Config.Symbol();
        symbolConfigB.setRewardMultiplier(3.0);

        Config.Symbol bonusSymbol = new Config.Symbol();
        bonusSymbol.setImpact(ImpactType.EXTRA_BONUS);
        bonusSymbol.setExtra(5.0);

        Config.WinCombination winCombinationA = new Config.WinCombination();
        winCombinationA.setRewardMultiplier(1.5);

        Config.WinCombination winCombinationB = new Config.WinCombination();
        winCombinationB.setRewardMultiplier(2.0);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of(
                "A", List.of(winCombinationA),
                "B", List.of(winCombinationB)
        );
        String bonusSymbolValue = "+5";
        Map<String, Config.Symbol> symbols = Map.of(
                "A", symbolConfigA,
                "B", symbolConfigB,
                "+5", bonusSymbol
        );
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(95.0, reward);
    }

    @Test
    public void testCalculateRewardCombinationOfWinningAndMultiplyBonusSymbols() {
        Config.Symbol symbolConfigA = new Config.Symbol();
        symbolConfigA.setRewardMultiplier(2.0);

        Config.Symbol symbolConfigB = new Config.Symbol();
        symbolConfigB.setRewardMultiplier(3.0);

        Config.Symbol bonusSymbol = new Config.Symbol();
        bonusSymbol.setImpact(ImpactType.MULTIPLY_REWARD);
        bonusSymbol.setRewardMultiplier(5.0);

        Config.WinCombination winCombinationA = new Config.WinCombination();
        winCombinationA.setRewardMultiplier(1.5);

        Config.WinCombination winCombinationB = new Config.WinCombination();
        winCombinationB.setRewardMultiplier(2.0);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of(
                "A", List.of(winCombinationA),
                "B", List.of(winCombinationB)
        );
        String bonusSymbolValue = "x5";
        Map<String, Config.Symbol> symbols = Map.of(
                "A", symbolConfigA,
                "B", symbolConfigB,
                "x5", bonusSymbol
        );
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(450.0, reward);
    }

    @Test
    public void testCalculateRewardCombinationOfWinningAndMissBonusSymbols() {
        Config.Symbol symbolConfigA = new Config.Symbol();
        symbolConfigA.setRewardMultiplier(2.0);

        Config.Symbol symbolConfigB = new Config.Symbol();
        symbolConfigB.setRewardMultiplier(3.0);

        Config.Symbol bonusSymbol = new Config.Symbol();
        bonusSymbol.setImpact(ImpactType.MISS);

        Config.WinCombination winCombinationA = new Config.WinCombination();
        winCombinationA.setRewardMultiplier(1.5);

        Config.WinCombination winCombinationB = new Config.WinCombination();
        winCombinationB.setRewardMultiplier(2.0);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of(
                "A", List.of(winCombinationA),
                "B", List.of(winCombinationB)
        );
        String bonusSymbolValue = "MISS";
        Map<String, Config.Symbol> symbols = Map.of(
                "A", symbolConfigA,
                "B", symbolConfigB,
                "MISS", bonusSymbol
        );
        double bettingAmount = 10.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(90.0, reward);
    }

    @Test
    public void testCalculateRewardZeroBettingAmount() {
        Config.Symbol symbolConfig = new Config.Symbol();
        symbolConfig.setRewardMultiplier(2.0);

        Config.WinCombination winCombination = new Config.WinCombination();
        winCombination.setRewardMultiplier(1.5);

        Map<String, List<Config.WinCombination>> winCombBySymbolMap = Map.of("A", List.of(winCombination));
        String bonusSymbolValue = "";
        Map<String, Config.Symbol> symbols = Map.of("A", symbolConfig);
        double bettingAmount = 0.0;

        double reward = RewardCalculator.calculateReward(winCombBySymbolMap, bonusSymbolValue, symbols, bettingAmount);
        assertEquals(0, reward);
    }

}