package com.vlayaki;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private static final int MAX_N_OF_BANKNOTES_IN_CELL = 10;

    @Test
    public void when_cell_have_space_validate_deposit_should_return_empty_optional() {
        Cell cell = createCell(Banknote.FIFTY, 5);
        Optional<String> errorMsg = cell.validateDeposit();
        assertTrue(errorMsg.isEmpty());
    }

    @Test
    public void when_cell_is_full_validate_deposit_should_return_error_msg() {
        Cell cell = createCell(Banknote.FIFTY, MAX_N_OF_BANKNOTES_IN_CELL);
        Optional<String> errorMsg = cell.validateDeposit();
        assertTrue(errorMsg.isPresent());
    }

    @Test
    public void when_deposit_is_executed_n_of_banknotes_should_be_increased() {
        Cell cell = createCell(Banknote.FIFTY, 6);
        int actual = cell.getNOfBanknotes();
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    public void when_cell_is_full_deposit_should_throw_exception() {
        Cell cell = createCell(Banknote.FIFTY, MAX_N_OF_BANKNOTES_IN_CELL);
        assertThrows(RuntimeException.class, () -> cell.deposit());
    }

    @Test
    public void when_cell_is_empty_max_withdrawal_amount_should_return_0() {
        Cell cell = createCell(Banknote.FIFTY, 0);
        int actual = cell.maxPossibleWithdrawalAmount(1000);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void when_nominal_is_500_and_requested_amount_is_1200_max_possible_withdrawal_should_return_1000() {
        Cell cell = createCell(Banknote.FIVE_HUNDREDS, 10);
        int actual = cell.maxPossibleWithdrawalAmount(1200);
        int expected = 1000;
        assertEquals(expected, actual);
    }

    @Test
    public void when_500_nominal_cell_have_2_banknotes_and_5000_is_requested_max_possible_withdrawal_should_return_1000() {
        Cell cell = createCell(Banknote.FIVE_HUNDREDS, 2);
        int actual = cell.maxPossibleWithdrawalAmount(5000);
        int expected = 1000;
        assertEquals(expected, actual);
    }

    @Test
    public void when_cell_is_empty_withdraw_should_return_empty_list() {
        Cell cell = createCell(Banknote.FIVE_HUNDREDS, 0);
        List<Banknote> actual = cell.withdraw(1200);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void when_requested_amount_greater_then_cell_nominal_withdraw_should_return_empty_list() {
        Cell cell = createCell(Banknote.FIVE_HUNDREDS, 1);
        List<Banknote> actual = cell.withdraw(200);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void when_1700_is_requested_500_nominal_cell_should_return_3_banknotes() {
        Cell cell = createCell(Banknote.FIVE_HUNDREDS, 10);
        List<Banknote> actual = cell.withdraw(1700);
        List<Banknote> expected = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> Banknote.FIVE_HUNDREDS)
                .collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }

    private Cell createCell(Banknote banknote, int nOfBanknotes) {
        Cell res = new Cell(banknote, MAX_N_OF_BANKNOTES_IN_CELL);
        for (int i = 0; i < nOfBanknotes; i++) {
            res.deposit();
        }
        return res;
    }
}