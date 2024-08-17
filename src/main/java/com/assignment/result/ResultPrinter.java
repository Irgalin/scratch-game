package com.assignment.result;

import com.assignment.config.enums.ImpactType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResultPrinter {

    public static void printResult(GameResult result) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode resultNode = mapper.valueToTree(result);

        if (result.getReward() == 0.0) {
            resultNode.remove("applied_winning_combinations");
            resultNode.remove("applied_bonus_symbol");
        } else if (ImpactType.MISS.getValue().equalsIgnoreCase(result.getAppliedBonusSymbol())) {
            resultNode.remove("applied_bonus_symbol");
        }

        String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
        System.out.println(jsonOutput);
    }

}
