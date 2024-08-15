package com.assignment.config;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        private String type;
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
        private String when;
        private Integer count;
        private String group;
        @JsonProperty("covered_areas")
        private List<List<String>> coveredAreas;
    }

}