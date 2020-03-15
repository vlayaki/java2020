package com.vlayaki;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class ATMImplTest {
    private Cells cells;
    private CardValidator cardValidator;
    private CellsValidator cellsValidator;
    private ATM atm;
    private Card card;

    @BeforeEach

    public void init() {
        cells = mock(Cells.class);
        cardValidator = mock(CardValidator.class);
        when(cardValidator.validateDeposit(any())).thenReturn(Optional.empty());
        cellsValidator = mock(CellsValidator.class);
        when(cellsValidator.validateDeposit(any())).thenReturn(Optional.empty());
        atm = new ATMImpl(cells, cardValidator, cellsValidator);
        card = mock(Card.class);
        atm.insertCard(card);
    }

    @Test
    public void when_card_validation_fails_deposit_should_throw_exception() {
        String cardErrorMsg = "card deposit error";
        when(cardValidator.validateDeposit(any())).thenReturn(Optional.of(cardErrorMsg));
        ATMImpl atm = new ATMImpl(cells, cardValidator, cellsValidator);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> atm.deposit(Banknote.HUNDRED));
        assertEquals(cardErrorMsg, exception.getMessage());
        verify(cells, never()).deposit(any());
    }

    @Test
    public void when_cells_validation_fails_deposit_should_throw_exception() {
        String errorMsg = "cells deposit error";
        when(cellsValidator.validateDeposit(any())).thenReturn(Optional.of(errorMsg));
        ATMImpl atm = new ATMImpl(cells, cardValidator, cellsValidator);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> atm.deposit(any()));
        assertEquals(errorMsg, exception.getMessage());
        verify(card, never()).deposit(anyInt());
        verify(cells, never()).deposit(any());
    }

    @Test
    public void when_validation_passes_deposit_should_be_executed_without_exceptions() {
        atm.deposit(Banknote.FIFTY);
        verify(card).deposit(anyInt());
        verify(cells).deposit(any());
    }

}