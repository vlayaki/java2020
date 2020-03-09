package com.vlayaki;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ATMImpl implements ATM {
    private final Cells cells;
    private final CardValidator cardValidator;
    private final CellsValidator cellsValidator;
    private Card card;

    @Override
    public void deposit(Banknote banknote) {
        Stream.of(
                cardValidator.validateDeposit(card),
                cellsValidator.validateDeposit(banknote))
                .filter(Optional::isPresent)
                .findFirst()
                .ifPresent((error) -> {
                    throw new RuntimeException(error.get());
                });
        card.deposit(banknote.getNominal());
        cells.deposit(banknote);
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        Stream.of(
                cardValidator.validateWithdrawal(card, amount),
                cellsValidator.validateWithdrawal(amount))
                .filter(Optional::isPresent)
                .findFirst()
                .ifPresent((error) -> {
                    throw new RuntimeException(error.get());
                });
        card.withdraw(amount);
        return cells.withdraw(amount);
    }

    @Override
    public double balance() {
        return card.getBalance();
    }

    @Override
    public void insertCard(Card card) {
        this.card = card;
    }

    @Override
    public void returnCard() {
        this.card = null;
    }
}
