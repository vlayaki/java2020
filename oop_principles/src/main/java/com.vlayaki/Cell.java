package com.vlayaki;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class Cell {
    private final Banknote banknote;
    private final int maxNOfBanknotes;
    @Getter
    private int nOfBanknotes = 0;

    public static final String CELL_IS_FULL_ERROR_MESSAGE = "There is no more space in cell";

    public Optional<String> validateDeposit(){
        if(nOfBanknotes == maxNOfBanknotes)
            return Optional.of(CELL_IS_FULL_ERROR_MESSAGE);
        return Optional.empty();
    }

    public void deposit() {
        if (nOfBanknotes == maxNOfBanknotes)
            throw new RuntimeException(CELL_IS_FULL_ERROR_MESSAGE);
        nOfBanknotes++;
    }

    public int maxPossibleWithdrawalAmount(int amount) {
        int nOfBanknotesRequired = amount / getNominal();
        return Math.min(nOfBanknotes, nOfBanknotesRequired) * getNominal();
    }

    public List<Banknote> withdraw(int amount) {
        List<Banknote> res = new ArrayList<>();
        int nToRemove = Math.min(amount / banknote.getNominal(), nOfBanknotes);
        nOfBanknotes -= nToRemove;
        for (int i = 0; i < nToRemove; i++) {
            res.add(banknote);
        }
        return res;
    }

    public int getNominal() {
        return banknote.getNominal();
    }
}
