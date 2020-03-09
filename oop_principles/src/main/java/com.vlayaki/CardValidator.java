package com.vlayaki;

import java.util.Optional;

public class CardValidator {
    private final static String CARD_NOT_FOUND_ERROR_MSG = "Please insert the card first";

    public Optional<String> validateDeposit(Card card) {
        if (card == null)
            return Optional.of(CARD_NOT_FOUND_ERROR_MSG);
        return Optional.empty();
    }

    public Optional<String> validateWithdrawal(Card card, int amount) {
        if (card == null)
            return Optional.of(CARD_NOT_FOUND_ERROR_MSG);
        return card.validateWithdrawal(amount);
    }
}
