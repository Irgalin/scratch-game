package com.assignment.config;

import com.assignment.config.deserializer.SymbolTypeDeserializer;
import com.assignment.config.deserializer.WinCombinationGroupTypeDeserializer;
import com.assignment.config.deserializer.WinCombinationWhenTypeDeserializer;
import com.assignment.config.enums.SymbolType;
import com.assignment.config.enums.WinCombinationGroupType;
import com.assignment.config.enums.WinCombinationWhenType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Config {

    private Integer columns;
    private Integer rows;

    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    @Data
    public static class Symbol {
        @JsonProperty(value = "reward_multiplier")
        private Double rewardMultiplier;
        @JsonDeserialize(using = SymbolTypeDeserializer.class)
        private SymbolType type;
        private Double extra;
        private String impact;
    }

    @Data
    public static class Probabilities {
        @JsonProperty("standard_symbols")
        private List<StandardSymbol> standardSymbols;
        @JsonProperty("bonus_symbols")
        private BonusSymbols bonusSymbols;

        @Data
        public static class StandardSymbol {
            private Integer column;
            private Integer row;
            private Map<String, Integer> symbols;
        }

        @Data
        public static class BonusSymbols {
            private Map<String, Integer> symbols;
        }
    }

    @Data
    public static class WinCombination {
        @JsonProperty(value = "reward_multiplier")
        private Double rewardMultiplier;
        @JsonDeserialize(using = WinCombinationWhenTypeDeserializer.class)
        private WinCombinationWhenType when;
        private Integer count;
        @JsonDeserialize(using = WinCombinationGroupTypeDeserializer.class)
        private WinCombinationGroupType group;
        @JsonProperty("covered_areas")
        private List<List<String>> coveredAreas;
    }

}