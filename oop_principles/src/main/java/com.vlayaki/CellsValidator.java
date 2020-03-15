package com.vlayaki;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CellsValidator {
    private final Cells cells;

    public Optional<String> validateDeposit(Banknote banknote){
        return cells.validateDeposit(banknote);
    }

    public Optional<String> validateWithdrawal(int amount) {
        return cells.validateWithdrawal(amount);
    }
}
