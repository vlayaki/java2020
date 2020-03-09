package com.vlayaki;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CellsTest {
    private static final int MAX_N_OF_BANKNOTES_IN_CELL = 10;

    @Test
    public void validate_withdrawal_should_return_empty_optional_when_cells_contain_requested_amount() {
        TreeMap<Banknote, Cell> banknoteToCell = new TreeMap<>();
        Arrays.stream(Banknote.values())
                .forEach((b) -> {
                    Cell cell = createCell(b, 5);
                    banknoteToCell.put(b, cell);
                });
        Cells cells = new Cells(banknoteToCell);
        Optional<String> actual = cells.validateWithdrawal(1200);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void when_cells_dont_contain_required_amount_validate_withdrawal_should_return_non_empty_optional() {
        TreeMap<Banknote, Cell> banknoteToCell = new TreeMap<>();
        banknoteToCell.put(Banknote.FIVE_HUNDREDS, createCell(Banknote.FIVE_HUNDREDS, 5));
        banknoteToCell.put(Banknote.HUNDRED, createCell(Banknote.HUNDRED, 5));
        Cells cells = new Cells(banknoteToCell);
        Optional<String> actual = cells.validateWithdrawal(850);
        assertTrue(actual.isPresent());
    }

    @Test
    public void withdrawal_should_return_requested_amount_with_min_number_of_banknotes() {
        TreeMap<Banknote, Cell> banknoteToCell = new TreeMap<>();
        Arrays.stream(Banknote.values())
                .forEach((b) -> {
                    Cell cell = createCell(b, 10);
                    banknoteToCell.put(b, cell);
                });
        Cells cells = new Cells(banknoteToCell);
        List<Banknote> actual = cells.withdraw(6700);
        List<Banknote> expected = Arrays.asList(
                Banknote.FIVE_THOUSANDS,
                Banknote.THOUSAND,
                Banknote.FIVE_HUNDREDS,
                Banknote.HUNDRED,
                Banknote.HUNDRED);
        assertIterableEquals(expected, actual);
    }

    private Cell createCell(Banknote banknote, int nOfBanknotes) {
        Cell cell = new Cell(banknote, MAX_N_OF_BANKNOTES_IN_CELL);
        for (int i = 0; i < nOfBanknotes; i++) {
            cell.deposit();
        }
        return cell;
    }
}