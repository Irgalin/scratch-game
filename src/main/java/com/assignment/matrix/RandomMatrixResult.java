package com.assignment.matrix;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomMatrixResult {
    private String[][] matrix;
    private BonusSymbolCell bonusSymbolCell;

    public String getBonusSymbolValue() {
        int row = bonusSymbolCell.row();
        int column = bonusSymbolCell.column();
        return matrix[row][column];
    }

}