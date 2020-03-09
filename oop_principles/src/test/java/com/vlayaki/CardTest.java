package com.vlayaki;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void when_deposit_850_balance_should_return_850() {
        Card card = new Card();
        card.deposit(850);
        int actual = card.getBalance();
        int expected = 850;
        assertEquals(expected, actual);
    }

    @Test
    public void when_balance_less_then_requested_amount_deposit_should_throw_exception() {
        Card card = new Card();
        card.deposit(500);
        assertThrows(RuntimeException.class, () -> card.withdraw(850));
    }

    @Test
    public void when_balance_greater_then_requested_amount_validate_withdrawal_should_return_empty_optional() {
        Card card = new Card();
        card.deposit(500);
        Optional<String> actual = card.validateWithdrawal(200);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void when_balance_less_then_requested_amount_validate_withdrawal_should_return_non_empty_optional() {
        Card card = new Card();
        card.deposit(500);
        Optional<String> actual = card.validateWithdrawal(850);
        assertTrue(actual.isPresent());
    }

}