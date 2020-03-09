package com.vlayaki;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Cells {
    private final SortedMap<Banknote, Cell> banknoteToCell;

    private final static String WITHDRAW_NOT_POSSIBLE_ERROR_MSG = "Operation is not possible, please try another amount";

    public Optional<String> validateDeposit(Banknote banknote) {
        return banknoteToCell.get(banknote).validateDeposit();
    }

    public void deposit(Banknote banknote) {
        banknoteToCell.get(banknote).deposit();
    }

    public Optional<String> validateWithdrawal(int amount) {
        Iterator<Cell> iterator = banknoteToCell.values().iterator();
        while (iterator.hasNext() && amount > 0) {
            amount -= iterator.next().maxPossibleWithdrawalAmount(amount);
        }
        return amount == 0
                ? Optional.empty()
                : Optional.of(WITHDRAW_NOT_POSSIBLE_ERROR_MSG);
    }

    public List<Banknote> withdraw(int amount) {
        List<Banknote> res = new ArrayList<>();
        Iterator<Cell> iterator = banknoteToCell.values().iterator();
        while (iterator.hasNext() && amount > 0) {
            Cell cell = iterator.next();
            List<Banknote> withdraw = cell.withdraw(amount);
            res.addAll(withdraw);
            amount -= withdraw.size() * cell.getNominal();
        }
        return res;
    }
}
