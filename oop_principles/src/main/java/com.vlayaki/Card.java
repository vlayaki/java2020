package com.vlayaki;

import lombok.Getter;

import java.util.Optional;

public class Card {
    @Getter
    private int balance = 0;
    private final static String NOT_ENOUGH_MONEY_ERROR_MESSAGE = "Sorry, not enough money";

    void deposit(int amount) {
        balance += amount;
    }

    void withdraw(int amount) {
        if(balance < amount)
            throw new RuntimeException(NOT_ENOUGH_MONEY_ERROR_MESSAGE);
        balance -= amount;
    }

    Optional<String> validateWithdrawal(int amount){
        if(balance < amount)
            return Optional.of(NOT_ENOUGH_MONEY_ERROR_MESSAGE);
        return Optional.empty();
    }
}
